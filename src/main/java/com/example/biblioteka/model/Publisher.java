package com.example.biblioteka.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publishers") // tabela iz baze
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id") // kolona iz baze
    private Long publisherId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    // getters & setters
    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }
}
