package com.example.biblioteka.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false)
    private String name;

    // Default constructor
    public Category() {}

    // Constructor sa parametrom
    public Category(String name) {
        this.name = name;
    }

    // Getter i Setter za categoryId
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    // Getter i Setter za name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
