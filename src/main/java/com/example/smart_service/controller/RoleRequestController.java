package com.example.smart_service.controller;

import com.example.smart_service.dto.response.RoleResponse;
import com.example.smart_service.service.RoleRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role-requests")
@RequiredArgsConstructor
public class RoleRequestController {

    private final RoleRequestService roleRequestService;

    @PostMapping("/submit")
public ResponseEntity<RoleResponse> submit(
    @RequestBody com.example.smart_service.dto.request.RoleRequest dto,
    @RequestHeader("Authorization") String authHeader // <--- This must be here
) {
    return ResponseEntity.ok(roleRequestService.createRequest(dto, authHeader));
}
}