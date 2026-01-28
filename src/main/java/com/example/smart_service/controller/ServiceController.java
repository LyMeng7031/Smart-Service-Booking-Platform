package com.example.smart_service.controller;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@RequestBody ServiceRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable("id") Long serviceId,
            @RequestBody ServiceRequest request
    ) {
        ServiceResponse response = serviceService.updateService(serviceId, request);
        return ResponseEntity.ok(response);
    }
}
