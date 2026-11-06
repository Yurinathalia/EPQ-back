package com.epq.epq_bakend.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.dto.LoginRequest;
import com.epq.epq_bakend.dto.LoginResponse;
import com.epq.epq_bakend.dto.RegisterRequest;
import com.epq.epq_bakend.dto.RegisterResponse;
import com.epq.epq_bakend.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}