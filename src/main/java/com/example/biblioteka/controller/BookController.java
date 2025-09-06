package com.example.biblioteka.controller;

import com.example.biblioteka.model.Book;
import com.example.biblioteka.model.Author;
import com.example.biblioteka.repository.BookRepository;
import com.example.biblioteka.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/books") // REST API pod /api/books
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // --- Vrati sve knjige ---
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // --- Vrati knjige po kategoriji ---
    @GetMapping("/category/{categoryId}")
    public List<Book> getBooksByCategory(@PathVariable Integer categoryId) {
        return bookRepository.findByCategory_CategoryId(categoryId);
    }

    // --- Vrati knjigu po ID ---
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Integer id) {
        return bookRepository.findById(id);
    }

    // --- Dodaj novu knjigu ---
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // --- Dodaj autore postojećoj knjizi ---
    @PostMapping("/{id}/authors")
    public Book addAuthorsToBook(@PathVariable Integer id, @RequestBody Iterable<Integer> authorIds) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Knjiga nije pronađena"));
        Set<Author> authors = Set.copyOf(authorRepository.findAllById(authorIds));
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    // --- Ažuriraj knjigu ---
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Knjiga nije pronađena"));
        book.setTitle(updatedBook.getTitle());
        book.setCategory(updatedBook.getCategory());
        book.setPublisher(updatedBook.getPublisher());
        book.setAuthors(updatedBook.getAuthors());
        return bookRepository.save(book);
    }

    // --- Obriši knjigu ---
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
    }
}
