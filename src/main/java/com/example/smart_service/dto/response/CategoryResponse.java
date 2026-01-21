package com.example.smart_service.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long Id;
    private String name;
    private String description;

    public CategoryResponse(Long Id, String name, String description) {
        this.Id = Id;
        this.name = name;
        this.description = description;
    }
}
