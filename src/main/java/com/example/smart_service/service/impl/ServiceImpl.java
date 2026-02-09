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

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements ServiceService {

        private final ServiceRepository serviceRepository;
        private final CategoryRepository categoryRepository;
        private final UserRepository userRepository;

        // create service//
        @Override
        @PreAuthorize("hasRole('provider')")
        public ServiceResponse createService(ServiceRequest request) {
                Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
                UserEntity user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                ServiceEntity service = new ServiceEntity();
                service.setTitle(request.getTitle());
                service.setDescription(request.getDescription());
                service.setPrice(request.getPrice());
                service.setDurationMinutes(request.getDurationMinutes());
                service.setCategory(category);
                service.setUser(user);

                ServiceEntity savedService = serviceRepository.save(service);

                return new ServiceResponse(
                                savedService.getServiceId(),
                                savedService.getTitle(),
                                savedService.getDescription(),
                                savedService.getPrice(),
                                savedService.getDurationMinutes(),
                                savedService.getCategory().getName());
        }

        // update service//
        @Override
        @PreAuthorize("hasRole('provider')")
        public ServiceResponse updateService(Long serviceId, ServiceRequest request) {
                Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
                ServiceEntity service = serviceRepository.findById(serviceId)
                                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

                if (!service.getUser().getId().equals(userId)) {
                        throw new RuntimeException("You are not allowed to update this service");
                }

                service.setTitle(request.getTitle());
                service.setDescription(request.getDescription());
                service.setPrice(request.getPrice());
                service.setDurationMinutes(request.getDurationMinutes());

                if (request.getCategoryId() != null) {
                        Category category = categoryRepository.findById(request.getCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                        service.setCategory(category);
                }

                ServiceEntity updatedService = serviceRepository.save(service);

                return new ServiceResponse(
                                updatedService.getServiceId(),
                                updatedService.getTitle(),
                                updatedService.getDescription(),
                                updatedService.getPrice(),
                                updatedService.getDurationMinutes(),
                                updatedService.getCategory().getName());
        }

        // get all services//
        @Override
        public List<ServiceResponse> getAllServices() {
                return serviceRepository.findAll().stream().map(service -> new ServiceResponse(
                                service.getServiceId(),
                                service.getTitle(),
                                service.getDescription(),
                                service.getPrice(),
                                service.getDurationMinutes(),
                                service.getCategory().getName())).collect(Collectors.toList());
        }

}
