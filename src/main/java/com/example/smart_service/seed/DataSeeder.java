package com.example.smart_service.seed;

import com.example.smart_service.entity.RoleEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.repository.RoleRepository;
import com.example.smart_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; 

@Override
public void run(String... args) {
    if (!userRepository.existsByEmail("admin@example.com")) {
        
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        
        // Assign roles...
        userRepository.save(admin);
    }
}
}