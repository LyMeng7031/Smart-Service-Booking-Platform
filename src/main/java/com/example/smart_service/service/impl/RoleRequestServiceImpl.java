package com.example.smart_service.service.impl;

import com.example.smart_service.dto.request.RoleRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.dto.response.RoleResponse;
import com.example.smart_service.entity.RoleEntity;
import com.example.smart_service.entity.RoleRequestEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.exception.BadRequestException;
import com.example.smart_service.exception.ResourceNotFoundException;
import com.example.smart_service.repository.RoleRepository;
import com.example.smart_service.repository.RoleRequestRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.service.RoleRequestService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleRequestServiceImpl implements RoleRequestService {

    private final RoleRequestRepository requestRepo;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

   @Override
   @PreAuthorize("hasRole('customer')")
    public RoleResponse createRequest(RoleRequest request) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (requestRepo.existsByUserId(userId)) {
                throw new BadRequestException("You have already requested");
        }

        if(user.getRoles().stream().anyMatch(role -> role.getName().equals("provider"))) {
                throw new BadRequestException("You are already a provider");
        }

        RoleRequestEntity roleRequest = new RoleRequestEntity();
        roleRequest.setBusinessName(request.getBusinessName());
        roleRequest.setBusinessBio(request.getBusinessBio());
        roleRequest.setUser(user);

        RoleRequestEntity savedRequest = requestRepo.save(roleRequest);

        AuthResponse userResponse = new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getPrifileImage(),
                user.getStatus(),
                null,
                null
        );

        return new RoleResponse(
                savedRequest.getId(),
                savedRequest.getBusinessName(),
                savedRequest.getBusinessBio(),
                savedRequest.getStatus(),
                userResponse
        );
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public List<RoleResponse> getAllRequests() {
        return requestRepo.findAll().stream()
                .map(request -> {
                    AuthResponse userResponse = new AuthResponse(
                            request.getUser().getId(),
                            request.getUser().getUsername(),
                            request.getUser().getEmail(),
                            request.getUser().getPassword(),
                            request.getUser().getPhone(),
                            request.getUser().getPrifileImage(),
                            request.getUser().getStatus(),
                            null,
                            null
                    );

                    return new RoleResponse(
                            request.getId(),
                            request.getBusinessName(),
                            request.getBusinessBio(),
                            request.getStatus(),
                            userResponse
                    );
                })
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public RoleResponse approve(Long requestId) {

        RoleRequestEntity request = requestRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        
        if(request.getStatus().equals("approved") || request.getStatus().equals("rejected")) {
            throw new BadRequestException("Request is already processed");
        }

        request.setStatus("approved");

        RoleEntity defaultRole = roleRepository.findByName("provider")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        UserEntity user = request.getUser();
        List<RoleEntity> updatedRoles = new ArrayList<>(user.getRoles());
        updatedRoles.add(defaultRole);
        user.setRoles(updatedRoles);
        userRepository.save(user);
        
        requestRepo.save(request);

        AuthResponse userResponse = new AuthResponse(
                request.getUser().getId(),
                request.getUser().getUsername(),
                request.getUser().getEmail(),
                request.getUser().getPassword(),
                request.getUser().getPhone(),
                request.getUser().getPrifileImage(),
                request.getUser().getStatus(),
                null,
                null
        );

        return new RoleResponse(
                request.getId(),
                request.getBusinessName(),
                request.getBusinessBio(),
                "approved",
                userResponse
        );
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public RoleResponse reject(Long requestId) {

        RoleRequestEntity request = requestRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if(request.getStatus().equals("approved") || request.getStatus().equals("rejected")) {
            throw new BadRequestException("Request is already processed");
        }
            request.setStatus("rejected");
            requestRepo.save(request);

            AuthResponse userResponse = new AuthResponse(
                    request.getUser().getId(),
                    request.getUser().getUsername(),
                    request.getUser().getEmail(),
                    request.getUser().getPassword(),
                    request.getUser().getPhone(),
                    request.getUser().getPrifileImage(),
                    request.getUser().getStatus(),
                    null,
                    null
            );

            return new RoleResponse(
                    request.getId(),
                    request.getBusinessName(),
                    request.getBusinessBio(),
                    "rejected",
                    userResponse
            );
    }
}