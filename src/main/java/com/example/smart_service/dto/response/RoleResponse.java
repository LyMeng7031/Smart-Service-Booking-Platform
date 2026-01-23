package com.example.smart_service.dto.response;

import lombok.*;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long requestId;
    private String username;
    private String status;
    private String requestedRole;
    private LocalDateTime createdAt;
}