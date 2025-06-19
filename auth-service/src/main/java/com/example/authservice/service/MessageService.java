package com.example.authservice.service;

import com.example.authservice.dto.EmailVerificationResult;
import com.example.authservice.model.Status;
import com.example.authservice.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private  AuthService authService;

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;


    public String sendEmailVerificationRequest(String topic, String email) throws JsonProcessingException {
        kafkaTemplate.send(topic, JsonUtil.toJson(email));
        return "Event published: " + JsonUtil.toJson(email);
    }

    @KafkaListener(topics = "email-verification-result", groupId = "example-group")
    public void emailIsVerifiedEvent(String result) {
        EmailVerificationResult model = JsonUtil.fromJson(result, EmailVerificationResult.class);
        if (model.verified()){
            authService.updateUserStatus(model.email(), Status.VERIFIED);
        }else {
            System.out.println("Email verification failed");
            authService.updateUserStatus(model.email(), Status.UNVERIFIED);

        }
    }

}
