package org.example.springwebsocket.app.controller;


import lombok.RequiredArgsConstructor;

import org.example.springwebsocket.app.model.User;
import org.example.springwebsocket.app.model.dto.AuthRequest;
import org.example.springwebsocket.app.model.dto.AuthResponse;
import org.example.springwebsocket.app.repository.UserRepository;

import org.example.springwebsocket.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .roles(Collections.singleton("USER"))
                .build();
        userRepo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
