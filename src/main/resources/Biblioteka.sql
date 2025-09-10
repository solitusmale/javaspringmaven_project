-- =======================================
-- Kreiranje baze
-- =======================================
DROP DATABASE IF EXISTS mala_biblioteka;
CREATE DATABASE mala_biblioteka;
USE mala_biblioteka;

-- =======================================
-- Tabela: roles
-- =======================================
CREATE TABLE IF NOT EXISTS roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- =======================================
-- Tabela: users
-- =======================================
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- =======================================
-- ManyToMany tabela: user_roles
-- =======================================
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- =======================================
-- Tabela: authors
-- =======================================
CREATE TABLE IF NOT EXISTS authors (
    author_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- =======================================
-- Tabela: categories
-- =======================================
CREATE TABLE IF NOT EXISTS categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- =======================================
-- Tabela: publishers
-- =======================================
CREATE TABLE IF NOT EXISTS publishers (
    publisher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- =======================================
-- Tabela: books
-- =======================================
CREATE TABLE IF NOT EXISTS books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    category_id INT,
    publisher_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    FOREIGN KEY (publisher_id) REFERENCES publishers(publisher_id) ON DELETE SET NULL
);

-- =======================================
-- ManyToMany tabela: book_authors
-- =======================================
CREATE TABLE IF NOT EXISTS book_authors (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);

-- =======================================
-- Tabela: borrow_records
-- =======================================
CREATE TABLE IF NOT EXISTS borrow_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE
);

-- =======================================
-- Insert uloge
-- =======================================
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- =======================================
-- Insert korisnici (BCrypt lozinke)
-- =======================================
INSERT INTO users (username, password) VALUES 
('admin', '$2a$12$nHceLEJpMDswh/PbcnhzUeiCs5tgVYSIEk6/1hpGWv95ZH.2M9iJG'),
('user1', '$2a$12$zputSYfBWFzB9hd6Asqtn.UmyW.HDiy9c2FYm9hORx3AEZgSiUS9G'),
('user2', '$2a$12$/R7mDLeiZ4B7Z9nKXeEYTeJDhMOFyF.lnf2Rbmht21ZoR5/c7AnbK');

-- =======================================
-- Poveži korisnike i role
-- =======================================
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- admin → ADMIN
(2, 2), -- user1 → USER
(3, 2); -- user2 → USER

-- =======================================
-- Insert kategorije
-- =======================================
INSERT INTO categories (name) VALUES 
('Roman'),
('Naučna fantastika'),
('Biografija');

-- =======================================
-- Insert autori
-- =======================================
INSERT INTO authors (name) VALUES 
('J.K. Rowling'),
('Isaac Asimov'),
('Walter Isaacson');

-- =======================================
-- Insert izdavači
-- =======================================
INSERT INTO publishers (name) VALUES 
('Bloomsbury'),
('Gnome Press'),
('Simon & Schuster');

-- =======================================
-- Insert knjige (sa publisher_id)
-- =======================================
INSERT INTO books (title, category_id, publisher_id) VALUES 
('Harry Potter', 1, 1),
('Foundation', 2, 2),
('Steve Jobs', 3, 3);

-- =======================================
-- Poveži knjige i autore
-- =======================================
INSERT INTO book_authors (book_id, author_id) VALUES 
(1, 1),
(2, 2),
(3, 3);

-- =======================================
-- Primer pozajmica
-- =======================================
INSERT INTO borrow_records (user_id, book_id, borrow_date) VALUES 
(2, 1, '2025-08-01');
