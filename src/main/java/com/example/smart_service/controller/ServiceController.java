package com.example.smart_service.controller;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.service.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ApiResponse<ServiceResponse> createService(@RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return ApiResponse.success("Service created successfully", response);
    }

    @PutMapping("update/{id}")
    public ApiResponse<ServiceResponse> updateService(
            @PathVariable("id") Long serviceId,
            @RequestBody ServiceRequest request
    ) {
        ServiceResponse response = serviceService.updateService(serviceId, request);
        return ApiResponse.success("Service updated successfully", response);
    }

    @GetMapping("/Services")
    public ApiResponse<List<ServiceResponse>> getAllServices() {
        List<ServiceResponse> services = serviceService.getAllServices();
        return ApiResponse.success("Services retrieved successfully", services);
    }
}
