package com.booktrack.model;

import java.time.LocalDate;

public class OpenLoanReport {

    private final String bookTitle;
    private final String bookIsbn;
    private final String memberName;
    private final String memberCode;
    private final LocalDate borrowedDate;
    private final LocalDate dueDate;
    private final boolean overdue;


    public OpenLoanReport(String bookTitle, String bookIsbn, String memberName, String memberCode, LocalDate borrowedDate, LocalDate dueDate, boolean overdue) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.memberName = memberName;
        this.memberCode = memberCode;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.overdue = overdue;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public boolean isOverdue() {
        return overdue;
    }
}
