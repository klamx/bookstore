package com.example.bookstore.error;

public class BookAlreadyExistException extends Exception{
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
