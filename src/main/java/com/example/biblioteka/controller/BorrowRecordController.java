package com.example.biblioteka.controller;

import com.example.biblioteka.model.BorrowRecord;
import com.example.biblioteka.model.User;
import com.example.biblioteka.repository.BorrowRecordRepository;
import com.example.biblioteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrow") // promenjen base path za REST API
public class BorrowRecordController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private UserRepository userRepository;

    // Endpoint za pozajmice trenutno ulogovanog korisnika
    @GetMapping("/my") // ostaje /my, ali je sada pod /api/borrow
    public List<BorrowRecord> getMyBorrows(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return borrowRecordRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
