package com.example.biblioteka.controller;

import com.example.biblioteka.model.Book;
import com.example.biblioteka.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookViewController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books/view")
    public String showBooks(Model model) {
        try {
            List<Book> books = bookRepository.findAll();
            model.addAttribute("books", books);
            return "books"; // vraća books.html iz templates
        } catch (Exception e) {
            e.printStackTrace(); // stack trace u konzoli za debag
            model.addAttribute("errorMessage", "Greška pri učitavanju knjiga: " + e.getMessage());
            return "error"; // napravi error.html u templates
        }
    }
}
