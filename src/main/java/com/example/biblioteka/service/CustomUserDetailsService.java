package com.example.biblioteka.service;

import com.example.biblioteka.model.User;
import com.example.biblioteka.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Učitaj korisnika iz baze zajedno sa rolama
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen: " + username));

        System.out.println("DEBUG -> Pokušan login za korisnika: " + username);
        System.out.println("DEBUG -> Lozinka iz baze: " + user.getPassword());
        System.out.println("DEBUG -> Role: " + user.getRoles().stream().map(r -> r.getName()).toList());

        // Pretvori role u GrantedAuthority sa "ROLE_" prefiksom
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        // Vrati Spring Security User objekat
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
