package com.example.bookstore.controller;

import com.example.bookstore.error.BookAlreadyExistException;
import com.example.bookstore.error.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestComponent
class BookControllerTest {

    @Autowired
    @Mock
    private  BookRepository bookRepository = Mockito.mock(BookRepository.class);
    @Autowired
    @InjectMocks
    public BookService bookServiceMock = new BookService(bookRepository);
    @Autowired
    public BookController bookController = new BookController(bookServiceMock);
    Book book = new Book();


    @BeforeEach
    void setUp() throws BookNotFoundException, BookAlreadyExistException {

        book.setId((long) 1);
        book.setAuthor("Some Author");
        book.setGenre("Some genre");
        book.setTitle("Some title");
        book.setPublicationYear("1998-06-08");

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
    }

    @Test
    void getAll() throws BookNotFoundException {
        List<Book> resBooks = bookController.getAll();
        Assertions.assertEquals(1,resBooks.stream().count());
    }

    @Test
    void createBook() throws BookAlreadyExistException {
        book.setId((long) 1);
        book.setAuthor("Some Author");
        book.setGenre("Some genre");
        book.setTitle("Some title");
        book.setPublicationYear("1998-06-08");
        ResponseEntity<Object> resObject = bookController.createBook(book);

        Assertions.assertEquals(HttpStatus.CREATED, resObject.getStatusCode());
    }

    @Test
    void updateBook() throws BookAlreadyExistException, BookNotFoundException {
        book.setId((long) 1);
        book.setAuthor("Some Author");
        book.setGenre("Some genre");
        book.setTitle("Some title");
        book.setPublicationYear("1998-06-08");

        // Configuración del mock del servicio
        when(bookServiceMock.updateBook(any(Book.class))).thenThrow(new BookNotFoundException("Book not found in the catalog"));

        // Llamada al método en el controlador y verificación de la excepción lanzada
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            ResponseEntity<Object> resObject = bookController.updateBook(book);
        });
    }

    @Test
    void deleteBook() {

    }
}