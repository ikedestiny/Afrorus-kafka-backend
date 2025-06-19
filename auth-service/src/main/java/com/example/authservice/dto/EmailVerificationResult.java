package com.example.authservice.dto;

import com.example.authservice.model.UserModel;
import org.apache.catalina.User;

public record EmailVerificationResult(
        String email,
        Boolean verified
) {}

