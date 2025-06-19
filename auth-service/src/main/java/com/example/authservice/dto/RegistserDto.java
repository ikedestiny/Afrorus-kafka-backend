package com.example.authservice.dto;

import com.example.authservice.model.Role;

public record RegistserDto(String username, String password, String email, Role role) {
}
