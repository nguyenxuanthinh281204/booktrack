package com.booktrack.ui;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.service.BookService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final BookService bookService;
    private final Scanner scanner;

    public ConsoleMenu(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    public void start(){

        boolean running = true;

        while (running){
            printMenu();

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> catalogueMenu();

                case "2" -> {
                    System.out.println("Members feature is not implemented yet.");
                }

                case "3" -> {
                    System.out.println("Loans feature is not implemented yet.");
                }

                case "4" -> {
                    System.out.println("Reports feature is not implemented yets. ");
                }

                case "5" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }

                default -> System.out.println("Invalid option. Please try again");
            }
        }
    }

    private void printMenu(){
        System.out.println();
        System.out.println("=== BookTrack Library ===");
        System.out.println("1. Open Catalogue.");
        System.out.println("2. Members");
        System.out.println("3. Loans");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.println("Choose an option: ");
    }

    private void catalogueMenu(){

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Catalogue ===");
            System.out.println("1. View all books");
            System.out.println("2. Add Book");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> showCatalogue();

                case "2" -> addBook();

                case "3" -> running = false;

                default -> System.out.println("Invalid option. Please try again");
            }
        }
    }

    private void showCatalogue(){

        List<Book> books = bookService.getAllBooks();

        System.out.println();
        System.out.println("=== Book Catalogue ===");

        if(books.isEmpty()){
            System.out.println("No books found");
            return;
        }

        for (Book book : books){
            System.out.println( book.getIsbn() + " | " + book.getTitle() + " | " + book.getAuthor() + " | "+ book.getCategory() + " | " + book.getStatus());
        }
    }

    public void addBook(){

        System.out.println();
        System.out.println("=== Add Book ===");

        System.out.println("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.println("Title: ");
        String title = scanner.nextLine();

        System.out.println("Author: ");
        String author = scanner.nextLine();

        System.out.println("Category: ");
        String category = scanner.nextLine();

        try {

            bookService.addBook(isbn,title,author,category);
            System.out.println("Book added successfully. ");
        } catch ( BusinessException e){
            System.out.println(" Rejected: "+ e.getMessage());
        }
    }

}
