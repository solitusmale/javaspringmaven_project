-- =======================================
-- Baza: mala_biblioteka
-- =======================================

CREATE DATABASE IF NOT EXISTS mala_biblioteka;
USE mala_biblioteka;

-- =======================================
-- Tabela: Users
-- =======================================
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER'
);

-- =======================================
-- Tabela: Authors
-- =======================================
CREATE TABLE IF NOT EXISTS Authors (
    author_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- =======================================
-- Tabela: Categories
-- =======================================
CREATE TABLE IF NOT EXISTS Categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- =======================================
-- Tabela: Books
-- =======================================
CREATE TABLE IF NOT EXISTS Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);

-- =======================================
-- ManyToMany tabela: Book_Authors
-- =======================================
CREATE TABLE IF NOT EXISTS Book_Authors (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (author_id) REFERENCES Authors(author_id)
);

-- =======================================
-- Tabela: BorrowRecords
-- =======================================
CREATE TABLE IF NOT EXISTS BorrowRecords (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);



-- =======================================
-- Primer podataka
-- =======================================
INSERT INTO Users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO Users (username, password, role) VALUES
('user1', 'user123', 'USER')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO Categories (name) VALUES ('Roman')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Categories (name) VALUES ('Naučna fantastika')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Categories (name) VALUES ('Biografija')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Authors (name) VALUES ('J.K. Rowling')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Authors (name) VALUES ('Isaac Asimov')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Authors (name) VALUES ('Walter Isaacson')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO Books (title, category_id) VALUES ( 'Harry Potter', 1 )
ON DUPLICATE KEY UPDATE title=title;

INSERT INTO Books (title, category_id) VALUES ( 'Foundation', 2 )
ON DUPLICATE KEY UPDATE title=title;

INSERT INTO Books (title, category_id) VALUES ( 'Steve Jobs', 3 )
ON DUPLICATE KEY UPDATE title=title;

INSERT INTO Book_Authors (book_id, author_id) VALUES (1,1)
ON DUPLICATE KEY UPDATE book_id=book_id;

INSERT INTO Book_Authors (book_id, author_id) VALUES (2,2)
ON DUPLICATE KEY UPDATE book_id=book_id;

INSERT INTO Book_Authors (book_id, author_id) VALUES (3,3)
ON DUPLICATE KEY UPDATE book_id=book_id;

INSERT INTO BorrowRecords (user_id, book_id, borrow_date) VALUES (2,1,'2025-08-01')
ON DUPLICATE KEY UPDATE record_id=record_id;
