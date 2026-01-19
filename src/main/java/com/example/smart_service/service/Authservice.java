package com.example.smart_service.service;

import org.springframework.stereotype.Service;

import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;

@Service
public interface Authservice {
    AuthResponse register(RegisterRequest request);
}
