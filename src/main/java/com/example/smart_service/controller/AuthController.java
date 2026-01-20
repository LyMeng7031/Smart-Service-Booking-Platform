package com.example.smart_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_service.dto.request.LoginRequest;
import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.service.Authservice;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final Authservice authservice;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authservice.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authservice.login(request);
    }

}
