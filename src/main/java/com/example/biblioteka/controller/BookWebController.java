package com.example.biblioteka.controller;

import com.example.biblioteka.model.Author;
import com.example.biblioteka.model.Book;
import com.example.biblioteka.model.Category;
import com.example.biblioteka.model.Publisher;
import com.example.biblioteka.repository.AuthorRepository;
import com.example.biblioteka.repository.BookRepository;
import com.example.biblioteka.repository.CategoryRepository;
import com.example.biblioteka.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BookWebController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    // Forma za dodavanje knjige
    @GetMapping("/books/manage/add")
    public String addBookForm(Model model) {
        Book book = new Book();
        List<Category> categories = categoryRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        List<Publisher> publishers = publisherRepository.findAll();

        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);

        return "book_add"; // book_add.html iz templates
    }

    
    // Forma za čuvanje nove knjige
    @PostMapping("/books/manage/add")
    public String saveBook(
            @ModelAttribute("book") Book book,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("publisherId") Long publisherId,
            @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
            Model model) {

        // Poveži kategoriju
        Category category = categoryRepository.findById(categoryId).orElse(null);
        book.setCategory(category);

        // Poveži izdavača
        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);
        book.setPublisher(publisher);

        // Poveži autore
        if (authorIds != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
            book.setAuthors(authors);
        }

        bookRepository.save(book);

        return "redirect:/books/view";
    }
}
