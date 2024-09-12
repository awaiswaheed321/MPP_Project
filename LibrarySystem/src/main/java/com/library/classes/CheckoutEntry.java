package com.library.classes;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable {
    private static final long serialVersionUID = -1887001394016169960L;
    private LibraryMember member;
    private BookCopy bookCopy;
    private LocalDate checkOutDate;
    private LocalDate dueDate;

    public CheckoutEntry(LibraryMember member, BookCopy bookCopy, LocalDate checkOutDate, LocalDate dueDate) {
        super();
        this.member = member;
        this.bookCopy = bookCopy;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LibraryMember getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "Book Info: " + "Title: " + bookCopy.getBook().getTitle() + ", Copy Num: " + bookCopy.getCopyNum() + " ";
    }
}
