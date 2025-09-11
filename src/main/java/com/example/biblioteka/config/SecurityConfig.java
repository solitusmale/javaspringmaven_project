package com.example.biblioteka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // isključi CSRF za testiranje (REST API POST/PUT/DELETE)
            .authorizeHttpRequests(authorize -> authorize
                // REST API pristup
                .requestMatchers("/api/books/**").hasRole("ADMIN") // samo admin može CRUD preko API-ja
                
                // MVC rute
                .requestMatchers("/books/manage/**").hasRole("ADMIN") // dodavanje, izmena, brisanje knjiga
                .requestMatchers("/books/view").hasAnyRole("USER", "ADMIN") // samo prikaz knjiga
                .requestMatchers("/borrow/**").hasRole("USER")

                
                // login, resources, home
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                
                .anyRequest().authenticated() // sve ostalo zahteva autentifikaciju
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403") // kreirati 403.html template
            );

        return http.build();
    }
}
