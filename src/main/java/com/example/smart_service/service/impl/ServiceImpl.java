package com.example.smart_service.service.impl;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.entity.Category;
import com.example.smart_service.entity.ServiceEntity;
import com.example.smart_service.entity.User;
import com.example.smart_service.repository.CategoryRepository;
import com.example.smart_service.repository.ServiceRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.security.JwtUtil;
import com.example.smart_service.service.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ServiceResponse createService(ServiceRequest request, String authHeader) {
        // Extract user ID from JWT token
        Long userId = jwtUtil.getUserIdFromToken(authHeader.substring(7));

        // Find user (provider)
        User provider = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        String token = authHeader.substring(7);

         // Assuming your UserEntity has a "role" field as String or Enum
        List<String> roles = jwtUtil.getRolesFromToken(token);

        if (roles == null || roles.stream().noneMatch(r -> r.equalsIgnoreCase("admin") || r.equalsIgnoreCase("provider"))) {
                throw new RuntimeException("You are not allowed to create a service");
        }

        // Find category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 4️ Create new service entity
        ServiceEntity service = new ServiceEntity();
        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setCategory(category);   // set the full Category object
        service.setUser(provider);       // set the provider

        // Save to database
        ServiceEntity savedService = serviceRepository.save(service);

        // Map to response
        return new ServiceResponse(
                savedService.getServiceId(),
                savedService.getTitle(),
                savedService.getDescription(),
                savedService.getPrice(),
                category.getName()
        );
    }

}
