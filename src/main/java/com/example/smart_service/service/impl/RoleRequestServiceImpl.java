package com.example.smart_service.service.impl;

import com.example.smart_service.dto.response.RoleResponse;
// import com.example.smart_service.entity.RoleRequest;
import com.example.smart_service.entity.User;
import com.example.smart_service.repository.RoleRequestRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.security.JwtUtil;
import com.example.smart_service.service.RoleRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleRequestServiceImpl implements RoleRequestService {

    private final RoleRequestRepository requestRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

   @Override
public RoleResponse createRequest(com.example.smart_service.dto.request.RoleRequest dto, String authHeader) {
    System.out.println("Received Header: " + authHeader); // Add this line to debug
    
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new RuntimeException("Invalid Authorization header");
    }
    // ... rest of code
     String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        // 2. Find User
        User userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Prevent Duplicates
        requestRepo.findByUser_IdAndStatus(userId, "PENDING")
                .ifPresent(r -> {
                    throw new RuntimeException("You already have a pending request.");
                });

        // 4. Create Entity with Business Data
        com.example.smart_service.entity.RoleRequest entity = new com.example.smart_service.entity.RoleRequest();
        entity.setUser(userEntity);
        entity.setStatus("PENDING");
        entity.setBusinessName(dto.getBusinessName());
        entity.setBusinessBio(dto.getBusinessBio());

        com.example.smart_service.entity.RoleRequest saved = requestRepo.save(entity);

        // 5. Build Response
        return RoleResponse.builder()
                .requestId(saved.getId())
                .username(userEntity.getUsername())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}