package com.example.smart_service.dto.response;

import lombok.Data;



@Data
public class RoleResponse {
    private Long requestId;
    private String businessName;
    private String businessBio;
    private String status;
    private AuthResponse user;

    public RoleResponse(Long requestId, String businessName, String businessBio, String status, AuthResponse user) {
        this.requestId = requestId;
        this.businessName = businessName;
        this.businessBio = businessBio;
        this.status = status;
        this.user = user;
    }
}