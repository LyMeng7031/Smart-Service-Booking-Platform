package com.example.smart_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.example.smart_service.dto.request.CategoryRequest;
import com.example.smart_service.dto.response.CategoryResponse;
import com.example.smart_service.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CategoryRequest request,
            @RequestHeader("Authorization") String authHeader) {
        return service.createCategory(request, authHeader);
    }

    @GetMapping("/get-all")
    public List<CategoryResponse> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("by-id/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request,
            @RequestHeader("Authorization") String authHeader) {
        return service.updateCategory(id, request, authHeader);
    }

}
