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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookWebController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    // -------------------- Prikaz svih knjiga --------------------
    @GetMapping("/view")
    public String viewBooks(Model model) {
        List<Book> books = bookRepository.findAll();

        // Debug ispis u konzolu
        books.forEach(b -> System.out.println(
                "Book: " + b.getTitle() +
                ", category: " + (b.getCategory() != null ? b.getCategory().getName() : "null")
        ));

        model.addAttribute("books", books);
        return "books"; // books.html iz templates
    }

    // -------------------- Dodavanje knjige --------------------
    @GetMapping("/manage/add")
    public String addBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("publishers", publisherRepository.findAll());
        return "book_add";
    }

    @PostMapping("/manage/add")
    public String saveBook(
            @ModelAttribute("book") Book book,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("publisherId") Long publisherId,
            @RequestParam(value = "authorIds", required = false) List<Integer> authorIds) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        book.setCategory(category);

        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);
        book.setPublisher(publisher);

        if (authorIds != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
            book.setAuthors(authors);
        }

        bookRepository.save(book);
        return "redirect:/books/view";
    }

    // -------------------- Izmena knjige --------------------
    @GetMapping("/manage/edit/{id}")
    public String editBookForm(@PathVariable("id") Integer id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "redirect:/books/view";
        }

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("publishers", publisherRepository.findAll());

        return "book_edit";
    }

    @PostMapping("/manage/edit")
    public String updateBook(
            @ModelAttribute("book") Book book,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("publisherId") Long publisherId,
            @RequestParam(value = "authorIds", required = false) List<Integer> authorIds) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        book.setCategory(category);

        Publisher publisher = publisherRepository.findById(publisherId).orElse(null);
        book.setPublisher(publisher);

        if (authorIds != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
            book.setAuthors(authors);
        } else {
            book.setAuthors(new HashSet<>());
        }

        bookRepository.save(book);
        return "redirect:/books/view";
    }

    // -------------------- Brisanje knjige --------------------
    @GetMapping("/manage/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookRepository.deleteById(id);
        return "redirect:/books/view";
    }
}
