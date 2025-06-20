package com.example.emailservice.controller;

import com.example.emailservice.dto.Email;
import com.example.emailservice.dto.EmailVerificationResult;
import com.example.emailservice.dto.VerificationDto;
import com.example.emailservice.service.EmailService;
import com.example.emailservice.service.MessageService;
import com.example.emailservice.service.RedisVerificationCodeService;
import com.example.emailservice.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;
@Slf4j
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


    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerificationDto verificationDto) {
        Integer cachedCode = redisVerificationCodeService.getVerificationCode(verificationDto.email());

        if (cachedCode == null) {
            return ResponseEntity.status(404).body("Verification code not found or expired");
        }

        log.info("Verifying code - Input: {}, Cached: {}", verificationDto.code(), cachedCode);

        if(cachedCode.equals(verificationDto.code())) {
            messageService.publishEvent("email-verification-result",
                    JsonUtil.toJson(new EmailVerificationResult(verificationDto.email(), true)));
            return ResponseEntity.ok("Successfully verified");
        } else {
            log.warn("Code mismatch for email: {}", verificationDto.email());
            messageService.publishEvent("email-verification-result",
                    JsonUtil.toJson(new EmailVerificationResult(verificationDto.email(), false)));
            return ResponseEntity.status(403).body("Wrong verification code");
        }
    }


}
