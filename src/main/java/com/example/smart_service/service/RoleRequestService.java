package com.example.smart_service.service;

import com.example.smart_service.dto.response.RoleResponse;

public interface RoleRequestService {
    // This is the method your Controller should call
    RoleResponse createRequest(com.example.smart_service.dto.request.RoleRequest dto, String authHeader);
}