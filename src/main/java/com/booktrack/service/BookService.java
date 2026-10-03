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
        isbn = normalizeIsbn(isbn);
        title = normalize(title);
        author = normalize(author);
        category = normalize(category);

        validateNotBlank(isbn, "ISBN");
        validateIsbn(isbn);

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

    private String normalizeIsbn(String isbn){
        if(isbn == null){
            return null;
        }

        return isbn.trim().replace("-","");
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

    private void validateIsbn(String isbn){
         if(!isbn.matches("\\d{10}|\\d{13}")){
             throw new BusinessException("ISBN must contain 10 or 13 digits.");
         }
    }
}
