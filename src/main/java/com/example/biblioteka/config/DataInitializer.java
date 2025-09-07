package com.example.biblioteka.config;

import com.example.biblioteka.model.Role;
import com.example.biblioteka.model.User;
import com.example.biblioteka.repository.RoleRepository;
import com.example.biblioteka.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
                
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user1").isEmpty()) {
                Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() -> new RuntimeException("USER role not found"));

                User user = new User();
                user.setUsername("user1");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}
