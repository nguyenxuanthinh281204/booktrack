package com.booktrack.repository;

import com.booktrack.model.Loan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryLoanRepository implements LoanRepository{

    private final Map<String, Loan> loans = new LinkedHashMap<>();
    @Override
    public void save(Loan loan) {

        loans.put(loan.getLoanId(),loan);
    }

    @Override
    public Loan findById(String loanId) {
        return loans.get(loanId);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }

    @Override
    public long countOpenLoansByMember(String memberCode) {
        return loans.values().stream().filter(Loan::isOpen).filter(loan -> loan.getMemberCode().equalsIgnoreCase(memberCode)).count();
    }
}
