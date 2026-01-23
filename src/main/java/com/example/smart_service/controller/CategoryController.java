package com.example.smart_service.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.example.smart_service.dto.request.CategoryRequest;
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
    public CategoryResponse createCategory(@RequestBody CategoryRequest request,
            @RequestHeader("Authorization") String authHeader) {
        return service.createCategory(request, authHeader);
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all service categories")
    @GetMapping("/get-all")
    public List<CategoryResponse> getAllCategories() {
        return service.getAllCategories();
    }


    @Operation(summary = "Get category by ID", description = "Retrieves a service category by its ID")
    @GetMapping("by-id/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @Operation(summary = "Update a category", description = "Updates an existing service category (Admin only)")
    @PutMapping("/update/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request,
            @RequestHeader("Authorization") String authHeader) {
        return service.updateCategory(id, request, authHeader);
    }

}
