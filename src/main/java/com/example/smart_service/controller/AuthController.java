package com.example.smart_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.service.Authservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Auth APIs")
@RequestMapping("api/auth")
public class AuthController {
    private final Authservice authservice;

    @Operation(summary = "Register new user", description = "Create new account with CUSTOMER role")
    @ApiResponse(responseCode = "200", description = "Register success", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authservice.register(request);
    }

}
