package com.example.smart_service.repository;

import com.example.smart_service.entity.RoleRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRequestRepository extends JpaRepository<RoleRequestEntity, Long> {

    boolean existsByUserId(Long userId);
}