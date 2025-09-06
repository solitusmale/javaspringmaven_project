package com.example.biblioteka.repository;

import com.example.biblioteka.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // JpaRepository već ima findById, findAll, save, deleteById itd.
}
