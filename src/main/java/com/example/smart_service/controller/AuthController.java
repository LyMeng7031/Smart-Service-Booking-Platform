package com.example.smart_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_service.dto.request.LoginRequest;
import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.service.Authservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Tag(name = "User API", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final Authservice authservice;

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authservice.register(request);
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns tokens") 
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authservice.login(request);
    }

}
