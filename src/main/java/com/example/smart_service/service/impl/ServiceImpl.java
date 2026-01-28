package com.example.smart_service.service.impl;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.entity.Category;
import com.example.smart_service.entity.ServiceEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.exception.ResourceNotFoundException;
import com.example.smart_service.repository.CategoryRepository;
import com.example.smart_service.repository.ServiceRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('provider')")
    public ServiceResponse createService(ServiceRequest request) {

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Find category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 4️ Create new service entity
        ServiceEntity service = new ServiceEntity();
        service.setTitle(request.getTitle());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setCategory(category);   
        service.setUser(user);     

        // Save to database
        ServiceEntity savedService = serviceRepository.save(service);

        // Map to response
        return new ServiceResponse(
                savedService.getServiceId(),
                savedService.getTitle(),
                savedService.getDescription(),
                savedService.getPrice(),
                savedService.getDurationMinutes(),
                savedService.getCategory().getName()
        );
    }

}
