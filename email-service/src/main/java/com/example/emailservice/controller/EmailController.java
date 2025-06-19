package com.example.emailservice.controller;

import com.example.emailservice.dto.Email;
import com.example.emailservice.dto.EmailVerificationResult;
import com.example.emailservice.dto.VerificationDto;
import com.example.emailservice.service.EmailService;
import com.example.emailservice.service.MessageService;
import com.example.emailservice.service.RedisVerificationCodeService;
import com.example.emailservice.util.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService service;

    private final  MessageService messageService;
    private final RedisVerificationCodeService redisVerificationCodeService;

    public EmailController(EmailService service, MessageService messageService, RedisVerificationCodeService redisVerificationCodeService) {
        this.service = service;
        this.messageService = messageService;
        this.redisVerificationCodeService = redisVerificationCodeService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Email email){
       return ResponseEntity.ok( service.sendEmail(email));
    }

    @PostMapping("/verify-code")
    /**
     * recieves an object containing the code and email and verifies it using the code cached in redis
     */
    public ResponseEntity<String> verifyCode(@RequestBody VerificationDto verificationDto){
        Integer cachedCode = redisVerificationCodeService.getVerificationCode(verificationDto.email());
        if (cachedCode == null) {
            return ResponseEntity.status(404).body("Verification code not found or expired");
        }
        if(cachedCode == verificationDto.code()){
            messageService.publishEvent("email-verification-result", JsonUtil.toJson(new EmailVerificationResult(verificationDto.email(), true)));
            return  ResponseEntity.ok("Successfully verified");
        }else{
            messageService.publishEvent("email-verification-result", JsonUtil.toJson(new EmailVerificationResult(verificationDto.email(), false)));
            return ResponseEntity.status(403).body("Wrong verification code");

        }
    }


}
