package com.example.emailservice.dto;


public record EmailVerificationResult(
       String email,
       Boolean verified
) {}

