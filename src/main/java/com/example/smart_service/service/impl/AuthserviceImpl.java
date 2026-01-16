package com.example.smart_service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.entity.RoleEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.repository.RoleRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.security.JwtUtil;
import com.example.smart_service.service.Authservice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthserviceImpl implements Authservice {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())
                || userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setPrifileImage(request.getPrifileImage());
        user.setStatus(request.getStatus());

        RoleEntity defaultRole = roleRepository.findByName("customer")
                .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        UserEntity savedUser = userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(savedUser.getId(), savedUser.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getId(), savedUser.getRoles());

        return new AuthResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(),
                savedUser.getPassword(), savedUser.getPhone(), savedUser.getPrifileImage(),
                savedUser.getStatus(), accessToken, refreshToken);
    }

}
