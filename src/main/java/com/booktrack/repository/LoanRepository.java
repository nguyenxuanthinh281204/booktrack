package com.booktrack.repository;

import com.booktrack.model.Loan;

import java.util.List;

public interface LoanRepository {

    void save(Loan loan);
    Loan findById(String loanId);
    List<Loan> findAll();
    long countOpenLoansByMember(String memberCode);
}
