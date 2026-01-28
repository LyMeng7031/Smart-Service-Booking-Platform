package com.example.smart_service.controller;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.service.ServiceService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService service;

    @Operation(summary = "Create a new service", description = "Creates a new service offered by a provider")
    @PostMapping("/create")
    public ApiResponse<ServiceResponse> create(@RequestBody ServiceRequest req) {
        ServiceResponse response = service.createService(req);
        return ApiResponse.success("Service created successfully", response);
    }
}