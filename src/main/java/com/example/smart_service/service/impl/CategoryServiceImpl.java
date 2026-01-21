package com.example.smart_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.smart_service.dto.request.CategoryRequest;
import com.example.smart_service.dto.response.CategoryResponse;
import com.example.smart_service.entity.Category;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.repository.CategoryRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.security.JwtUtil;
import com.example.smart_service.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public CategoryResponse createCategory(CategoryRequest request, String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        String token = authHeader.substring(7);

        // 2. Role Check (Check this BEFORE hitting the DB)
        List<String> roles = jwtUtil.getRolesFromToken(token);
        if (roles == null || !roles.contains("admin")) {
            throw new RuntimeException("You are not allowed to create a category");
        }

        // 3. User Extraction
        Long userId = jwtUtil.getUserIdFromToken(token);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUser(user);

        Category saved = repository.save(category);

        return new CategoryResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription());
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return repository.findAll().stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());
    }

    // @Override
    // public CategoryResponse getCategoryById(Long id) {
    // Category category = repository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Category not found"));
    // return mapToResponse(category);
    // }

    // @Override
    // public CategoryResponse updateCategory(Long id, CategoryRequest request) {
    // Category category = repository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Category not found"));

    // category.setName(request.getName());
    // category.setDescription(request.getDescription());

    // Category updated = repository.save(category);
    // return mapToResponse(updated);
    // }

    // @Override
    // public void deleteCategory(Long id) {
    // repository.deleteById(id);
    // }

    // private CategoryResponse mapToResponse(Category category) {
    // CategoryResponse response = new CategoryResponse();
    // response.setCategoryId(category.getCategoryId());
    // response.setName(category.getName());
    // response.setDescription(category.getDescription());
    // return response;
    // }
}
