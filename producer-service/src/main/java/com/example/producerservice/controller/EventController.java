package com.example.producerservice.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String publishEvent(@RequestBody String message) {
        kafkaTemplate.send("example-topic", message);
        return "Event published: " + message;
    }
}
