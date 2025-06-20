package com.example.emailservice;

import com.example.emailservice.service.RedisVerificationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class EmailServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RedisVerificationCodeService redisService;

    @Test
    void testCodeStorageAndRetrieval() {
        String testEmail = "test@example.com";
        int testCode = 654321;

        // Save code
        redisService.saveVerificationCode(testEmail, testCode);

        // Retrieve and verify
        Integer retrievedCode = redisService.getVerificationCode(testEmail);
        assertEquals(Optional.of(testCode), retrievedCode);
    }

}
