package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(value = "SELECT COUNT(*) FROM books WHERE books.author LIKE CONCAT('',?1,'') AND books.title LIKE CONCAT('',?2,'')", nativeQuery = true)
    int findAllByTitleAndAuthor(String author, String title);

    @Query(value = "SELECT COUNT(*) FROM books WHERE books.author LIKE CONCAT('',?1,'') AND books.title LIKE CONCAT('',?2,'') AND books.id != ?3", nativeQuery = true)
    int findAllByTitleAndAuthorAndID(String author, String title, Long id);

    Optional<Book> findById(Long id);
}
