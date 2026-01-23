package com.example.smart_service.controller;

import java.util.List;

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

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return service.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
    }
}
