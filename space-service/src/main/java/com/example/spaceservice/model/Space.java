package com.example.spaceservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;
    private SpaceType type;
    private Double weightKg; // nullable for documents
    private Double pricePerKg; // in rubles
    private String currency; // "RUB" or "NGN"
    private String description;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private String departureCity;
    private String arrivalCity;
    private boolean isVerifiedUser;
    private LocalDateTime createdAt;
}
