package com.example.smart_service.service;

import java.util.List;

import com.example.smart_service.dto.request.CategoryRequest;
import com.example.smart_service.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

}
