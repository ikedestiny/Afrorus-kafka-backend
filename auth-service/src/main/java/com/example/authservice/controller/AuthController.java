package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.RegistserDto;
import com.example.authservice.model.Status;
import com.example.authservice.model.UserModel;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private  AuthService authService;
    @Autowired
    private MessageService messageService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistserDto user) throws JsonProcessingException {
        UserModel model = new UserModel();
        model.setPassword(user.password());
        model.setRole(user.role());
        model.setEmail(user.email());
        model.setUsername(user.username());
        model.setStatus(Status.PENDING);
        messageService.sendEmailVerificationRequest("new-user", model.getEmail());
        return ResponseEntity.ok(authService.registerUser(model) + "status pending till email verified");
        //the rest will be handled in message service and email-verification microservice.
    }




}
