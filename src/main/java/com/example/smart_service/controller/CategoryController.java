package com.example.smart_service.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.example.smart_service.dto.request.CategoryRequest;
import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.CategoryResponse;
import com.example.smart_service.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "Create a new category", description = "Creates a new service category (Admin only)")
    @PostMapping("/create")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse response = service.createCategory(request);
        return ApiResponse.success("Category created successfully", response);
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all service categories")
    @GetMapping("/get-categories")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responses = service.getAllCategories();
        return ApiResponse.success("Categories retrieved successfully", responses);
    }


    @Operation(summary = "Get category by ID", description = "Retrieves a service category by its ID")
    @GetMapping("/get-category/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = service.getCategoryById(id);
        return ApiResponse.success("Category retrieved successfully", response);
    }

    @Operation(summary = "Update a category", description = "Updates an existing service category (Admin only)")
    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        CategoryResponse response = service.updateCategory(id, request);
        return ApiResponse.success("Category updated successfully", response);
    }

}
