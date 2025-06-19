package com.example.spaceservice.utils;



import com.example.spaceservice.dto.SpaceAvailableForDocDto;
import com.example.spaceservice.dto.SpaceAvailableForLoadDto;
import com.example.spaceservice.dto.SpaceNeedeForDocDto;
import com.example.spaceservice.dto.SpaceNeededForLoadDto;
import com.example.spaceservice.model.Space;
import com.example.spaceservice.model.SpaceType;

import java.time.LocalDateTime;

public class SpaceMapper {

    public static Space from(SpaceAvailableForDocDto r) {
        return baseBuilder(SpaceType.DOCUMENT_AVAILABLE, r.userId(), r.departureCity(), r.arrivalCity(), r.currency(),null, r.availableFrom(), r.availableTo(), r.isVerifiedUser())
                .build();
    }

    public static Space from(SpaceNeedeForDocDto r) {
        return baseBuilder(SpaceType.DOCUMENT_NEEDED, r.userId(), r.departureCity(), r.arrivalCity(),null,r.description(),r.availableFrom(),r.availableTo(),null)
                .build();
    }

    public static Space from(SpaceAvailableForLoadDto r) {
        return baseBuilder(SpaceType.LOAD_AVAILABLE, r.userId(), r.departureCity(), r.arrivalCity(), r.currency(), null, r.availableFrom(), r.availableTo(), r.isVerifiedUser())
                .weightKg(r.weightKg())
                .pricePerKg(r.pricePerKg())
                .build();
    }

    public static Space from(SpaceNeededForLoadDto r) {
        return baseBuilder(SpaceType.LOAD_NEEDED, r.userId(), r.departureCity(), r.arrivalCity(),null,r.description(),r.availableFrom(),r.availableTo(),null)
                .weightKg(r.weightKg())
                .build();
    }

    private static Space.SpaceBuilder baseBuilder(SpaceType type, String userId, String departureCity, String arrivalCity, String currency, String description, LocalDateTime from, LocalDateTime to, Boolean verified) {
        return Space.builder()
                .type(type)
                .userId(userId)
                .departureCity(departureCity)
                .arrivalCity(arrivalCity)
                .currency(currency)
                .description(description)
                .availableFrom(from)
                .availableTo(to)
                .isVerifiedUser(verified)
                .createdAt(LocalDateTime.now());
    }
}
