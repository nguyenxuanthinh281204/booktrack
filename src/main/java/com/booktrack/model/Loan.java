package com.booktrack.model;

import java.time.LocalDate;

public class Loan {

    private final String loanId;
    private final String bookIsbn;
    private final String memberCode;
    private final LocalDate borrowedDate;
    private final LocalDate dueDate;

    private LocalDate returnedDate;

    public Loan( String loanId,String bookIsbn, String memberCode, LocalDate borrowedDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.bookIsbn = bookIsbn;
        this.memberCode = memberCode;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean isOpen(){
        return returnedDate == null;
    }

    public void markReturned(LocalDate returnedDate){
        if(!isOpen()){
            throw new IllegalStateException("Loan has already been returned.");
        }
        this.returnedDate= returnedDate;
    }
}
