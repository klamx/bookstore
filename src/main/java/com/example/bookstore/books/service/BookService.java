package com.example.bookstore.books.service;

import com.example.bookstore.books.model.Book;
import com.example.bookstore.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public ResponseEntity<Object> createBook(Book book) {
        int res = this.bookRepository.findAllByTitleAndAuthor(book.getAuthor(), book.getTitle());
        HashMap<String, Object> response = new HashMap<>();
        if (res > 0) {
            response.put("success", false);
            response.put("message", "Book already exist in the catalog");
            response.put("related books", res);

            return new ResponseEntity<Object>(
                    response,
                    HttpStatus.CONFLICT
            );
        }

        this.bookRepository.save(book);
        response.put("success", true);
        response.put("message","Created successfuly");
        response.put("book", book);

        return new ResponseEntity<Object>(
                response,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateBook(Book book) {
        Optional<Book> res = this.bookRepository.findById(book.getId());
        int count = this.bookRepository.findAllByTitleAndAuthorAndID(book.getAuthor(), book.getTitle(), book.getId());

        HashMap<String, Object> response = new HashMap<>();
        if (count > 0) {
            response.put("success", false);
            response.put("message", "Book already exist in the catalog");

            return new ResponseEntity<Object>(
                    response,
                    HttpStatus.CONFLICT
            );
        }
        if (!res.isPresent()) {

            response.put("success", false);
            response.put("message", "Book not found in the catalog");

            return new ResponseEntity<Object>(
                    response,
                    HttpStatus.NOT_FOUND
            );
        }

        this.bookRepository.save(book);
        response.put("success", true);
        response.put("message","Updated successfuly");
        response.put("book", book);

        return new ResponseEntity<Object>(
                response,
                HttpStatus.ACCEPTED
        );

    }

    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }
}
