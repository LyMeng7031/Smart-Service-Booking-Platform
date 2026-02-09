package com.example.smart_service.controller;

import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.RoleResponse;
import com.example.smart_service.service.RoleRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/role-requests")
@RequiredArgsConstructor
@Tag(name = "Role Request API", description = "Endpoints for managing role requests")
@SecurityRequirement(name = "bearerAuth")
public class RoleRequestController {

    private final RoleRequestService roleRequestService;

    @Operation(summary = "Create Role Request", description = "Allows a customer to create a role request to become a service provider")
    @PostMapping("/provider-role")
    public ApiResponse<RoleResponse> createRoleRequest(@RequestBody com.example.smart_service.dto.request.RoleRequest request) {
        RoleResponse response = roleRequestService.createRequest(request);
        return ApiResponse.success("Role request created successfully", response);
    }

    @Operation(summary = "Get All Role Requests", description = "Allows an admin to retrieve all role requests")
    @GetMapping("/GetRequests")
    public ApiResponse<List<RoleResponse>> getAllRequests() {
        List<RoleResponse> response = roleRequestService.getAllRequests();
        return ApiResponse.success("All requests retrieved successfully", response);
    }

    @Operation(summary = "Approve Role Request", description = "Allows an admin to approve a role request")
    @PostMapping("/Approve/{requestId}")
    public ApiResponse<RoleResponse> approveRequest(@PathVariable Long requestId) {
        RoleResponse response = roleRequestService.approve(requestId);
        return ApiResponse.success("Request approved successfully", response);
    }

    @Operation(summary = "Reject Role Request", description = "Allows an admin to reject a role request")
    @PostMapping("/Reject/{requestId}")
    public ApiResponse<RoleResponse> rejectRequest(@PathVariable Long requestId) {
        RoleResponse response = roleRequestService.reject(requestId);
        return ApiResponse.success("Request rejected successfully", response);
        
    }
}