package com.example.emailservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class RedisVerificationCodeService {
    private final StringRedisTemplate redisTemplate; // Changed to StringRedisTemplate
    private static final Duration EXPIRATION = Duration.ofMinutes(10);

    public RedisVerificationCodeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveVerificationCode(String email, int code) {
        String cleanEmail = email.replace("\"", "");
        String key = "verification_code:" + cleanEmail;
        log.debug("Storing code {} with key {}", code, key);

        redisTemplate.opsForValue().set(
                key,
                String.valueOf(code),
                EXPIRATION // Using Duration instead of raw seconds
        );
    }

    public Integer getVerificationCode(String email) {
        String key = "verification_code:" + email;
        String code = redisTemplate.opsForValue().get(key);

        if (code == null) {
            log.debug("No code found for key {}", key);
            return null;
        }

        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            log.error("Invalid code format in Redis for key {}: {}", key, code);
            return null;
        }
    }

    // ... rest remains the same ...
}