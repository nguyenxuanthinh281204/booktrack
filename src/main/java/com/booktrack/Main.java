package com.booktrack;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Book;
import com.booktrack.repository.BookRepository;
import com.booktrack.repository.InMemoryBookRepository;
import com.booktrack.service.BookService;
import com.booktrack.ui.ConsoleMenu;

import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        BookRepository bookRepository = new InMemoryBookRepository();

        BookService bookService = new BookService(bookRepository);

        ConsoleMenu menu = new ConsoleMenu(bookService);

        menu.start();
    }
}