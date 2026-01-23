package com.example.smart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.smart_service.entity.RoleRequest;
import com.example.smart_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<RoleRequest> findByUsername(String username);

}
