package com.example.emailservice.service;

import com.example.emailservice.dto.Email;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisVerificationCodeService redisVerificationCodeService;

    public EmailService(JavaMailSender mailSender, RedisVerificationCodeService redisVerificationCodeService) {
        this.mailSender = mailSender;
        this.redisVerificationCodeService = redisVerificationCodeService;
    }


    public String sendEmail(Email email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());
        message.setFrom("ikeholy65@gmail.com");

        mailSender.send(message);
        return "Successfully sent: "+email.toString();
    }

    @KafkaListener(topics = "new-user", groupId = "example-group")
    public void sendVerificationCode(String email) {
        int randomSixDigit = ThreadLocalRandom.current().nextInt(100_000, 1_000_000); // from 100000 to 999999
        sendEmail(new Email(email,"Email Verification","Verification Code: "+randomSixDigit));
        redisVerificationCodeService.saveVerificationCode(email,randomSixDigit);

    }
}
