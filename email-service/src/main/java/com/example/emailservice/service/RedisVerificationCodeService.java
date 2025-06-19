package com.example.emailservice.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class RedisVerificationCodeService {

    private final StringRedisTemplate redisTemplate;
    private static final long EXPIRE_MINUTES = 10;

    public RedisVerificationCodeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveVerificationCode(String email, int code) {
        String key = getKey(email);
        long expireMillis = EXPIRE_MINUTES * 60 * 1000;
        redisTemplate.opsForValue().set(key, String.valueOf(code), expireMillis);    }

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
