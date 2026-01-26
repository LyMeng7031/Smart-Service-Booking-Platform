package com.example.smart_service.seed;

import com.example.smart_service.entity.RoleEntity;
import com.example.smart_service.entity.User;
import com.example.smart_service.repository.RoleRepository;
import com.example.smart_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${USERNAME}")
    private String username;

    @Value("${PASSWORD}")
    private String password;

    @Value("${EMAIL}")
    private String email;

    @Override
    public void run(String... args) {
        boolean emailExists = userRepository.existsByEmail(email);
        boolean usernameExists = userRepository.existsByUsername(username);

        if (!usernameExists && !emailExists) {

            User admin = new User();
            admin.setUsername(username);
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));

            RoleEntity adminRole = roleRepository.findByName("admin")
                    .orElseThrow(() -> {
                        throw new RuntimeException("Admin role not found");
                    });
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println(" Admin user created successfully");
        } else {
            System.out.println("Admin user already exists, skipping creation");
        }
    }
}