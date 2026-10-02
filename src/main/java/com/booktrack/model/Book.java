package com.booktrack.model;

public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private final String category;

    private BookStatus status;

    public Book( String isbn, String title,String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = BookStatus.AVAILABLE;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void borrow(){
        if(status != BookStatus.AVAILABLE){
            throw new IllegalStateException(
                    "Book is not available"
            );
        }
        status = BookStatus.ON_LOAN;
    }

    public void returnBook(){
        if(status != BookStatus.ON_LOAN){
            throw new IllegalStateException(
                    "Book is not currently on loan."
            );
        }
        status = BookStatus.AVAILABLE;
    }
}
