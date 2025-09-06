package com.example.biblioteka.repository;

import com.example.biblioteka.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByCategory_CategoryId(Integer categoryId);
}
