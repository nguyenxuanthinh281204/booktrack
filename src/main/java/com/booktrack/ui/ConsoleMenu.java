package com.booktrack.ui;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.*;
import com.booktrack.service.BookService;
import com.booktrack.service.LoanService;
import com.booktrack.service.MemberService;
import com.booktrack.service.ReportService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanService loanService;
    private final ReportService reportService;
    private final Scanner scanner;

    public ConsoleMenu(BookService bookService, MemberService memberService, LoanService loanService, ReportService  reportService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.loanService = loanService;
        this.reportService = reportService;
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

                case "3" -> loanMenu();

                case "4" -> reportMenu();

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

    private void loanMenu(){
        boolean running = true;

        while (running){
            System.out.println();
            System.out.println("=== Loan ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> borrowBook();

                case "2" -> returnBook();

                case "3" -> running = false;

                default -> System.out.println("Invalid. Try Again");
            }
        }
    }

    private void borrowBook(){
        System.out.println("");
        System.out.println("=== Borrow Book");
        System.out.print("Isbn: ");
        String isbn = scanner.nextLine();

        System.out.print("Member Code: ");
        String memberCode = scanner.nextLine();

        try {
            Loan loan = loanService.borrowBook(isbn, memberCode);

            System.out.println("Books borrowed successfully");

            System.out.println("LoanId "+ loan.getLoanId());

            System.out.println("Borrowed date: "+ loan.getBorrowedDate());

            System.out.println("Due date: "+ loan.getDueDate());
        } catch(BusinessException e) {
            System.out.println("Rejected " + e.getMessage());
        }
    }

    private void returnBook(){

        System.out.println();
        System.out.println("=== Return Book ===");

        System.out.print("Loan Id: ");
        String loanId = scanner.nextLine();

        try {
            loanService.returnBook(loanId);
            System.out.println("Book returned successfully");
        }catch (BusinessException e){
            System.out.println("Rejected: "+e.getMessage());
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

    private void reportMenu(){
        boolean running = true;

        while (running){
            System.out.println();
            System.out.println("=== Report ===");
            System.out.println("1. Available Books");
            System.out.println("2. Open Loans");
            System.out.println("3. Member Loan History");
            System.out.println("4. Top 3 Members");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1" -> showAvalableBooks();
                case "2" -> showOpenLoans();
                case "3" -> showMemberLoanHistory();
                case "4" -> showTopThreeMembers();
                case "5" -> running =false;
                default -> System.out.println("Invalid options. Please try again.");
            }
        }
    }

    private void showAvalableBooks(){
        System.out.println();
        System.out.println("=== Available Books ===");

        List<Book> books = reportService.getAvailableBooksReport();

        if(books.isEmpty()){
            System.out.println("No available books.");
            return;
        }

        for(Book book:books){
            System.out.println(book.getTitle() + " | "+book.getIsbn());
        }
    }

    private void showOpenLoans(){
        System.out.println();
        System.out.println("=== Open Loans ===");

        List<OpenLoanReport> reports = reportService.getOpenLoansReport();

        if(reports.isEmpty()){
            System.out.println("No open loans.");
            return;
        }

        for(OpenLoanReport report : reports){
            System.out.println(
                    report.getBookTitle() + " | "+report.getBookIsbn()+" | "+report.getMemberName()+" | "+ report.getMemberCode()+" | Borrowed: "+ report.getBorrowedDate()+ " | Due: "+report.getDueDate()+" | Overdue: "+ report.isOverdue()
            );
        }
    }

    private void showMemberLoanHistory(){
        System.out.println();
        System.out.println("=== Member Loan History ===");

        System.out.println("Member Code: ");
        String memberCode = scanner.nextLine().trim();

        List<Loan> loans = reportService.getLoanHistory(memberCode);

        if(loans.isEmpty()){
            System.out.println("No loan history found.");
            return;
        }

        for (Loan loan : loans){
            System.out.println("Loan Id: "+loan.getLoanId()+" | Isbn: "+ loan.getBookIsbn()+ " | Borrowed: "+ loan.getBorrowedDate()+ " | Due: "+loan.getDueDate()+" | Returned: "+ loan.getReturnedDate());
        }
    }

    private void showTopThreeMembers(){
        System.out.println();
        System.out.println("=== Top 3 Members by Total Loans ===");
        List<MemberLoanRanking> rankings = reportService.getTopThreeMembersByLoans();

        if(rankings.isEmpty()){
            System.out.println("No members found.");
            return;
        }

        for (MemberLoanRanking ranking : rankings){
            System.out.println(ranking.getMemberCode()+ " | "+ranking.getFullName()+ " | Total Loans: "+ranking.getTotalLoans());
        }
    }
}
