package com.library.domain;

import java.io.Serial;
import java.io.Serializable;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {
    @Serial
    private static final long serialVersionUID = -63976228084869815L;
    private final Book book;
    private final int copyNum;
    private boolean isAvailable;

    BookCopy(Book book, int copyNum, boolean isAvailable) {
        this.book = book;
        this.copyNum = copyNum;
        this.isAvailable = isAvailable;
    }

    BookCopy(Book book, int copyNum) {
        this.book = book;
        this.copyNum = copyNum;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCopyNum() {
        return copyNum;
    }

    public Book getBook() {
        return book;
    }

    public void changeAvailability() {
        isAvailable = !isAvailable;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (!(ob instanceof BookCopy copy)) return false;
        return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
    }
}
