package com.example.biblioteka.controller;

import com.example.biblioteka.model.Author;
import com.example.biblioteka.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    // Dobavi sve autore
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Dobavi autora po ID-u
    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Integer id) {
        return authorRepository.findById(id);
    }

    // Dodaj novog autora
    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    // Izmeni autora
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Integer id, @RequestBody Author updatedAuthor) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setName(updatedAuthor.getName());
        return authorRepository.save(author);
    }

    // Obrisi autora
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        authorRepository.deleteById(id);
    }
}
