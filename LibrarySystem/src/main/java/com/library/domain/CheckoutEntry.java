package com.library.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable {
    @Serial
    private static final long serialVersionUID = -1887001394016169960L;
    private final LibraryMember member;
    private final BookCopy bookCopy;
    private final LocalDate checkOutDate;
    private final LocalDate dueDate;

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
