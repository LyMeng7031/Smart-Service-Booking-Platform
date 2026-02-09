package com.example.smart_service.service;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {

    ServiceResponse createService(ServiceRequest request);

    ServiceResponse updateService(Long serviceId, ServiceRequest request);

    List<ServiceResponse> getAllServices();
}
