package com.example.smart_service.service;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;
import org.springframework.stereotype.Service;

@Service
public interface ServiceService {

    // Create a new service
    ServiceResponse createService(ServiceRequest request);

    // Update an existing service
    ServiceResponse updateService(Long serviceId, ServiceRequest request);
}
