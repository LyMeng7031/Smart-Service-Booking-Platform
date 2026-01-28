package com.example.smart_service.repository;

import com.example.smart_service.entity.RoleRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRequestRepository extends JpaRepository<RoleRequestEntity, Long> {
    // The underscore 'User_Id' is required because the field in RoleRequest is
    // named 'user'
    Optional<RoleRequestEntity> findByUser_IdAndStatus(Long userId, String status);
}