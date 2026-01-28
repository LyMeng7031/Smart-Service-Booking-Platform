package com.example.smart_service.service;

import org.springframework.stereotype.Service;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;

@Service
public interface ServiceService {
    ServiceResponse createService(ServiceRequest request);
}