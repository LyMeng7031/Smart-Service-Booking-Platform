package com.example.smart_service.repository;

import com.example.smart_service.entity.RoleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {
    // The underscore 'User_Id' is required because the field in RoleRequest is named 'user'
    Optional<RoleRequest> findByUser_IdAndStatus(Long userId, String status);
}