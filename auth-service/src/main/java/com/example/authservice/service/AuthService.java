package com.example.authservice.service;

import com.example.authservice.model.Status;
import com.example.authservice.model.UserModel;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private  AuthenticationManager authManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private  UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;



    public String authenticate(String username, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }

    public String registerUser(UserModel user){
        user.setPassword(encoder.encode(user.getPassword()));
        return "successfully registered user "+ userRepository.save(user);
    }

    public void updateUserStatus(String email, Status verified) {
        Optional<UserModel> userOpt = userRepository.findByEmail(email);

        userOpt.ifPresent(user -> {
            user.setStatus(verified);
            userRepository.save(user);
        });

        // TODO log or handle the case when user is not found
    }

}
