package com.example.biblioteka.controller;

import com.example.biblioteka.model.BorrowRecord;
import com.example.biblioteka.repository.BorrowRecordRepository;
import com.example.biblioteka.repository.UserRepository;
import com.example.biblioteka.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BorrowRecord> getBorrowRecordById(@PathVariable Long id) {
        return borrowRecordRepository.findById(id);
    }

    @PostMapping
    public BorrowRecord addBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        userRepository.findById(borrowRecord.getUser().getId()).orElseThrow();
        bookRepository.findById(borrowRecord.getBook().getBookId()).orElseThrow();

        return borrowRecordRepository.save(borrowRecord);
    }

    @PutMapping("/{id}")
    public BorrowRecord updateBorrowRecord(@PathVariable Long id, @RequestBody BorrowRecord updatedRecord) {
        BorrowRecord record = borrowRecordRepository.findById(id).orElseThrow();
        record.setUser(updatedRecord.getUser());
        record.setBook(updatedRecord.getBook());
        record.setBorrowDate(updatedRecord.getBorrowDate());
        record.setReturnDate(updatedRecord.getReturnDate());
        return borrowRecordRepository.save(record);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordRepository.deleteById(id);
    }
}
