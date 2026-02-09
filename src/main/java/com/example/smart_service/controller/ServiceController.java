package com.example.smart_service.controller;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.service.ServiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Service API", description = "Endpoints for managing services")
@SecurityRequirement(name = "bearerAuth")
public class ServiceController {

    private final ServiceService serviceService;

    @Operation(summary = "Create Service", description = "Allows a service provider to create a new service")
    @PostMapping
    public ApiResponse<ServiceResponse> createService(@RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return ApiResponse.success("Service created successfully", response);
    }

    @Operation(summary = "Update Service", description = "Allows a service provider to update an existing service")
    @PutMapping("update/{id}")
    public ApiResponse<ServiceResponse> updateService(
            @PathVariable("id") Long serviceId,
            @RequestBody ServiceRequest request
    ) {
        ServiceResponse response = serviceService.updateService(serviceId, request);
        return ApiResponse.success("Service updated successfully", response);
    }

    @Operation(summary = "Get All Services", description = "Retrieves a list of all services")
    @GetMapping("/Services")
    public ApiResponse<List<ServiceResponse>> getAllServices() {
        List<ServiceResponse> services = serviceService.getAllServices();
        return ApiResponse.success("Services retrieved successfully", services);
    }
}
