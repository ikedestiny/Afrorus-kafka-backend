package com.example.authservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class UserModel {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String email;
    private Role role;
    private Status status;
}
