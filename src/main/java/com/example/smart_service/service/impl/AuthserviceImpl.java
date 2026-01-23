package com.example.smart_service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.smart_service.dto.request.LoginRequest;
import com.example.smart_service.dto.request.RegisterRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.entity.RoleEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.exception.BadRequestException;
import com.example.smart_service.exception.ResourceNotFoundException;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())
                || userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Username or email already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setPrifileImage(request.getProfileImage());
        user.setStatus(request.getStatus());

        RoleEntity defaultRole = roleRepository.findByName("customer")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
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

    @Override
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Wrong password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getRoles());

        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getPhone(), user.getPrifileImage(),
                user.getStatus(), accessToken, refreshToken);
    }

}
