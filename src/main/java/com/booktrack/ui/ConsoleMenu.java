package com.booktrack.ui;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.model.Member;
import com.booktrack.service.BookService;
import com.booktrack.service.MemberService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final BookService bookService;
    private final MemberService memberService;
    private final Scanner scanner;

    public ConsoleMenu(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.scanner = new Scanner(System.in);
    }

    public void start(){

        boolean running = true;

        while (running){
            printMenu();

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> catalogueMenu();

                case "2" -> memberMenu();

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
            System.out.println("3. Find book by ISBN");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> showCatalogue();

                case "2" -> addBook();

                case "3" -> findBookByIsbn();

                case "4" -> running = false;

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

    private void memberMenu(){
        boolean running = true;

        while (running){
            System.out.println();
            System.out.println("=== Member ===");
            System.out.println("1. Register member");
            System.out.println("2. View all members");
            System.out.println("3. Find member by code");
            System.out.println("4. Find member by email");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> registerMember();

                case "2" -> showMembers();

                case "3" -> findMemberByCode();

                case "4" -> findMemberByEmail();

                case "5" -> running = false;

                default -> System.out.println("Invalid. Please try again.");
            }
        }
    }

    private void registerMember(){
        System.out.println();
        System.out.println("=== Register Member ===");

        System.out.println("Member code: ");
        String code = scanner.nextLine();

        System.out.println("Full name: ");
        String fullName = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        try {
            memberService.registerMember(code, fullName, email);
            System.out.println("Member registered successfully.");
        } catch (BusinessException e){
            System.out.println("Rejected: "+e.getMessage());
        }
    }

    public void showMembers(){
        List<Member> members = memberService.getAllMembers();

        System.out.println();
        System.out.println("=== Member List ===");

        if(members.isEmpty()){
            System.out.println("No members found.");
            return;
        }

        for(Member member : members){
            System.out.println("Code: "+member.getCode()+" | Name: "+member.getFullName()+ " | Email: "+member.getEmail());
        }
    }

    public void findMemberByCode(){
        System.out.println("Enter member code: ");

        String code = scanner.nextLine();

        Member member = memberService.findByCode(code);

        if(member == null){
            System.out.println("No member found.");
            return;
        }

        System.out.println("Code: "+member.getCode()+" | Name: "+ member.getFullName()+" | Email: "+member.getEmail());
    }

    public void findMemberByEmail(){
        System.out.println("Enter member email: ");

        String email = scanner.nextLine();

        Member member = memberService.findByEmail(email);

        if(member == null){
            System.out.println("No member found.");
            return;
        }
        System.out.println("Code: "+member.getCode()+" | Name: " + member.getFullName()+" | Email: "+ member.getEmail());
    }

    private void findBookByIsbn(){
        System.out.println("Enter ISBN: ");
        String isbn = scanner.nextLine();
        try{
            Book book = bookService.findByIsbn(isbn);
            if(book == null){
                System.out.println("No book found.");
                return;
            }

            System.out.println("ISBN: "+book.getIsbn()+" | Title: "+ book.getTitle()+" | Author: "+book.getAuthor()+" | Category: "+ book.getCategory()+" | Status: "+ book.getStatus());
        } catch (BusinessException e){
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
