package com.example.bookstore.controller;

import com.example.bookstore.error.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    public final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAll() throws BookNotFoundException {
        return this.bookService.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody Book book) {
        return this.bookService.createBook(book);
    }

    @PutMapping
    public ResponseEntity<Object> updateBook(@RequestBody Book book) {
        return this.bookService.updateBook(book);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        this.bookService.deleteBook(id);
    }

}
