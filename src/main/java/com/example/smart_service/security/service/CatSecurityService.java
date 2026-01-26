package com.example.smart_service.security.service;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import com.example.smart_service.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component("catSecurityService")
@RequiredArgsConstructor
public class CatSecurityService {
    private final CategoryRepository repository;

    public boolean isOwner(Long categoryId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Get the current logged-in user ID from the token subject
        Long currentUserId = Long.valueOf(authentication.getName());

        return repository.findById(categoryId)
                .map(category -> category.getUser().getId().equals(currentUserId))
                .orElse(false);
    }
}
