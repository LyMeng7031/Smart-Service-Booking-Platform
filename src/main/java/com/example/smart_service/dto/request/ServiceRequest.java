package com.example.smart_service.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationMinutes;
    private Long categoryId;
}