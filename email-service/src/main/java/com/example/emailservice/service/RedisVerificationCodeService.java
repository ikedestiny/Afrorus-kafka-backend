package com.example.emailservice.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisVerificationCodeService {

    private final StringRedisTemplate redisTemplate;
    private static final long EXPIRE_MINUTES = 1;

    public RedisVerificationCodeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveVerificationCode(String email, int code) {
        String key = getKey(email);
        redisTemplate.opsForValue().set(key, String.valueOf(code), EXPIRE_MINUTES, TimeUnit.MINUTES);
    }

    public Integer getVerificationCode(String email) {
        String key = getKey(email);
        String codeStr = redisTemplate.opsForValue().get(key);
        if (codeStr == null) {
            return null;  // expired or not found
        }
        return Integer.valueOf(codeStr);
    }

    public void removeVerificationCode(String email) {
        String key = getKey(email);
        redisTemplate.delete(key);
    }

    private String getKey(String email) {
        return "verification_code:" + email;
    }
}
