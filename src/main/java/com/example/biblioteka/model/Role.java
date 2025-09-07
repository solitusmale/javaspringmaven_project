package com.example.biblioteka.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")   // <-- mapiranje na role_id
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name; // ADMIN, USER

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // GETTERI I SETTERI
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}
