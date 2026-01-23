package com.example.smart_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.smart_service.dto.request.CategoryRequest;
import com.example.smart_service.dto.response.CategoryResponse;
import com.example.smart_service.entity.Category;
<<<<<<< HEAD
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.exception.ResourceNotFoundException;
=======
import com.example.smart_service.entity.User;
>>>>>>> 827e59b (Finish to create service)
import com.example.smart_service.repository.CategoryRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.service.CategoryService;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('admin')")
    public CategoryResponse createCategory(CategoryRequest request) {

<<<<<<< HEAD
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
=======
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
>>>>>>> 827e59b (Finish to create service)

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

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription());
    }

    @Override
    @Transactional
    @PreAuthorize("@catSecurityService.isOwner(#id, authentication)")
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = repository.save(category);
        return new CategoryResponse(
                updated.getId(),
                updated.getName(),
                updated.getDescription());
    }

}