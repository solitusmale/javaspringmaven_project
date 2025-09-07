package com.example.biblioteka.controller;

import com.example.biblioteka.model.User;
import com.example.biblioteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // koristi se za hash lozinki

    // Dobavi sve korisnike
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Dobavi korisnika po ID-u
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) { // Long umesto Integer
        return userRepository.findById(id);
    }

    // Dodaj novog korisnika
    @PostMapping
    public User addUser(@RequestBody User user) {
        // Hash lozinke pre cuvanja
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Izmeni korisnika
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        // Hash lozinke pre cuvanja
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setRoles(updatedUser.getRoles());
        return userRepository.save(user);
    }

    // Obrisi korisnika
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
