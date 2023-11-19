package com.example.bookstore.service;

import com.example.bookstore.error.BookAlreadyExistException;
import com.example.bookstore.error.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
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

    public List<Book> getAllBooks() throws BookNotFoundException {
        List<Book> books = this.bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("Books not found in the catalog");
        }
        return books;
    }

    public ResponseEntity<Object> createBook(Book book) throws BookAlreadyExistException {
        int res = this.bookRepository.findAllByTitleAndAuthor(book.getAuthor(), book.getTitle());
        HashMap<String, Object> response = new HashMap<>();
        if (res > 0) {
            throw new BookAlreadyExistException("Book already exist in the catalog");
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

    public ResponseEntity<Object> updateBook(Book book) throws BookAlreadyExistException, BookNotFoundException {
        Optional<Book> res = this.bookRepository.findById(book.getId());
        int count = this.bookRepository.findAllByTitleAndAuthorAndID(book.getAuthor(), book.getTitle(), book.getId());

        HashMap<String, Object> response = new HashMap<>();
        if (count > 0) {
            throw new BookAlreadyExistException("Book already exist in the catalog");
        }
        if (!res.isPresent()) {
            throw new BookNotFoundException("Book not found in the catalog");
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
