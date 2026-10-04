package com.booktrack.repository;

import com.booktrack.model.Book;

import java.util.*;

public class InMemoryBookRepository implements BookRepository{

    private final Map<String, Book> books = new LinkedHashMap<>();
    @Override
    public boolean existsByIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    @Override
    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
