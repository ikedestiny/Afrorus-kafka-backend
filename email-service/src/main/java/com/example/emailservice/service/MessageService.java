package com.example.emailservice.service;

import com.example.emailservice.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;


    public String publishEvent(String topic, String message){
        kafkaTemplate.send(topic, message);
        return "Event published: " + message;
    }





}
