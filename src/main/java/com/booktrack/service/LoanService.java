package com.booktrack.service;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.model.BookStatus;
import com.booktrack.model.Loan;
import com.booktrack.model.Member;
import com.booktrack.repository.BookRepository;
import com.booktrack.repository.LoanRepository;
import com.booktrack.repository.MemberRepository;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.UUID;

public class LoanService {

    private static final int MAX_OPEN_LOANS =3;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public LoanService(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    public Loan borrowBook(String isbn, String memberCode){
        isbn = normalize(isbn);
        memberCode = normalize(memberCode);

        validateNotBlank(isbn,"ISBN");
        validateNotBlank(memberCode,"Member Code");

        Book book = bookRepository.findByIsbn(isbn);
        if(book == null){
            throw new BusinessException("Book with Isbn " + isbn + " does not exists.");
        }

        Member member = memberRepository.findByCode(memberCode);

        if(member == null){
            throw new BusinessException("Member with code "+memberCode+"does not exist.");
        }

        if(book.getStatus() != BookStatus.AVAILABLE){
            throw new BusinessException("Book is not currently available");
        }

        long openLoans = loanRepository.countOpenLoansByMember(memberCode);

        if(openLoans >= MAX_OPEN_LOANS){
            throw new BusinessException("Member cannot have more than 3 open loans");
        }

        LocalDate borrowedDate = LocalDate.now();
        LocalDate dueDate = borrowedDate.plusDays(14);
        String loanId = UUID.randomUUID().toString();

        Loan loan = new Loan(loanId,isbn,memberCode,borrowedDate,dueDate);
        book.borrow();
        loanRepository.save(loan);
        return loan;
    }

    public void returnBook(String loanId){
        loanId = normalize(loanId);
        validateNotBlank(loanId,"Loan Id");
        Loan loan = loanRepository.findById(loanId);

        if(loan==null){
            throw new BusinessException("Loan with Id "+ loanId + " does not exists.");
        }

        if(!loan.isOpen()){
            throw new BusinessException("Loan has already been returned.");
        }

        Book book = bookRepository.findByIsbn(loan.getBookIsbn());

        if(book== null){
            throw new BusinessException("Book with Isbn "+loan.getBookIsbn()+" does not exists.");
        }

        LocalDate returnedDate = LocalDate.now();
        loan.markReturned(returnedDate);
        book.returnBook();
    }

    private String normalize(String value){
        if(value == null){
            return null;
        }

        return value.trim();

    }

    private void validateNotBlank(String value, String fieldName){
        if(value == null || value.isBlank()){
            throw new BusinessException(fieldName + "must not be blank");
        }
    }
}
