package com.booktrack.repository;

import com.booktrack.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void save(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAll();

    boolean existsByIsbn(String isbn);
}
