package com.example.spaceservice.dto;

import java.time.LocalDateTime;

public record SpaceAvailableForLoadDto(
        String userId,
        String departureCity,
        String arrivalCity,
        Double weightKg,
        Double pricePerKg,
        String currency,
        LocalDateTime availableFrom,
        LocalDateTime availableTo,
        boolean isVerifiedUser
) {}
