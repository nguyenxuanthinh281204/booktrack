package com.booktrack.service;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public void addBook(
            String isbn,
            String title,
            String author,
            String category
    ){
        isbn = normalize(isbn);
        title = normalize(title);
        author = normalize(author);
        category = normalize(category);

        validateNotBlank(isbn, "ISBN");
        validateNotBlank(title,"Title");
        validateNotBlank(author,"Author");
        validateNotBlank(category, "Category");

        if(bookRepository.existsByIsbn(isbn)){
            throw new BusinessException(
                    "A book with ISBN " + isbn + " already exists."
            );
        }

        Book book = new Book(isbn,title,author,category);

        bookRepository.save(book);

    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    private String normalize(String value){
        if(value == null){
            return null;
        }
        return value.trim();
    }

    private  void validateNotBlank(
            String value, String fieldName
    ){
        if(value == null || value.isBlank()){
            throw new BusinessException(
                    fieldName + " must not be blank."
            );
        }
    }
}
