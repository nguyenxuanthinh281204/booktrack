package com.booktrack.service;

import com.booktrack.model.*;
import com.booktrack.repository.BookRepository;
import com.booktrack.repository.LoanRepository;
import com.booktrack.repository.MemberRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ReportService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public ReportService(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getAvailableBooksReport(){
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getStatus()== BookStatus.AVAILABLE)
                .sorted(
                        java.util.Comparator
                                .comparing(Book::getTitle)
                                .thenComparing(Book::getIsbn)
                )
                .toList();
    }

    public List<OpenLoanReport> getOpenLoansReport(){
        LocalDate today = LocalDate.now();

        return loanRepository.findAll()
                .stream()
                .filter(Loan::isOpen)
                .map(loan -> {
                    Book book = bookRepository.findByIsbn(loan.getBookIsbn());
                    Member member = memberRepository.findByCode(loan.getMemberCode());

                    boolean overdue = today.isAfter(loan.getDueDate());

                    return  new OpenLoanReport(
                            book.getTitle(),
                            book.getIsbn(),
                            member.getFullName(),
                            member.getCode(),
                            loan.getBorrowedDate(),
                            loan.getDueDate(),
                            overdue
                    );
                })
                .toList();
    }

    public List<Loan> getLoanHistory(String memberCode){
        return loanRepository.findAll()
                .stream()
                .filter(loan -> loan.getMemberCode().equalsIgnoreCase(memberCode))
                .toList();
    }

    public List<MemberLoanRanking> getTopThreeMembersByLoans(){
        return memberRepository.findAll()
                .stream()
                .map(member -> {
                    long totalLoans = loanRepository.findAll()
                            .stream()
                            .filter(loan ->
                                    loan.getMemberCode()
                                            .equalsIgnoreCase(member.getCode())
                            )
                            .count();

                    return new MemberLoanRanking(
                            member.getCode(),
                            member.getFullName(),
                            totalLoans
                    );
                })
                .sorted(
                        Comparator
                                .comparing(MemberLoanRanking::getTotalLoans)
                                .reversed()
                                .thenComparing(MemberLoanRanking::getFullName)
                                .thenComparing(MemberLoanRanking::getMemberCode)

                ).limit(3)
                .toList();
    }
}
