package com.example.smart_service.service;

import java.util.List;

import com.example.smart_service.dto.request.RoleRequest;
import com.example.smart_service.dto.response.RoleResponse;

public interface RoleRequestService {
    RoleResponse createRequest(RoleRequest request);

    List<RoleResponse> getAllRequests();

    RoleResponse approve(Long requestId);

    RoleResponse reject(Long requestId);

    RoleResponse createRequest(RoleRequest dto, String authHeader);
}