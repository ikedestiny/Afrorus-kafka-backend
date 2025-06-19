package com.example.consumerservice.service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {

    @KafkaListener(topics = "example-topic", groupId = "example-group")
    public void consumeEvent(String message) {
        System.out.println("Received event: " + message);
    }
}