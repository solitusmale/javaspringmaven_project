package com.example.biblioteka.controller;

import com.example.biblioteka.model.Book;
import com.example.biblioteka.model.BorrowRecord;
import com.example.biblioteka.model.User;
import com.example.biblioteka.repository.BookRepository;
import com.example.biblioteka.repository.BorrowRecordRepository;
import com.example.biblioteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BorrowWebController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // -----------------------------------
    // Pozajmi knjigu (samo korisnik)
    // -----------------------------------
    @GetMapping("/borrow/add/{bookId}")
    public String borrowBook(@PathVariable Integer bookId, 
                             @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(user);
        record.setBorrowDate(LocalDate.now());
        borrowRecordRepository.save(record);

        return "redirect:/borrow/my"; 
    }

    // -----------------------------------
    // Prikaz mojih pozajmica (samo korisnik)
    // -----------------------------------
    @GetMapping("/borrow/my")
    public String myBorrows(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<BorrowRecord> borrows = borrowRecordRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        model.addAttribute("borrows", borrows);
        return "my_borrows"; // Thymeleaf template
    }
}
