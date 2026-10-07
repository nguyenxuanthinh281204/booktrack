package com.booktrack;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.repository.*;
import com.booktrack.service.BookService;
import com.booktrack.service.LoanService;
import com.booktrack.service.MemberService;
import com.booktrack.ui.ConsoleMenu;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        BookRepository bookRepository = new InMemoryBookRepository();
        BookService bookService = new BookService(bookRepository);

        MemberRepository memberRepository = new InMemoryMemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        LoanRepository loanRepository = new InMemoryLoanRepository();
        LoanService loanService = new LoanService(bookRepository, memberRepository, loanRepository);

        ConsoleMenu menu = new ConsoleMenu(bookService,memberService,loanService);

        menu.start();


    }
}