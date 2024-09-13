package com.library.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

final public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 6110690276685962829L;
    private final List<BookCopy> copies;
    private final List<Author> authors;
    private final String isbn;
    private final String title;
    private final int maxCheckoutLength;

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.authors = Collections.unmodifiableList(authors);
        copies = new ArrayList<>();
        copies.add(new BookCopy(this, 1, true));
    }

    public void addCopy() {
        copies.add(new BookCopy(this, copies.size() + 1, true));
    }

    public void addCopy(int copyNum) {
        copies.add(new BookCopy(this, copyNum, true));
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Book b = (Book) ob;
        return b.isbn.equals(isbn);
    }

    public boolean isAvailable() {
        if (copies == null || copies.isEmpty()) {
            return false;
        }
        return copies.stream().anyMatch(BookCopy::isAvailable);
    }

    @Override
    public String toString() {
        return "isbn: " + isbn + ", maxLength: " + maxCheckoutLength + ", available: " + isAvailable();
    }

    public int getNumCopies() {
        return copies.size();
    }

    public String getTitle() {
        return title;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getAuthorsDisplay() {
        String authors = "";
        for (int i = 0; i < this.authors.size(); i++) {
            Author author = this.authors.get(i);
            authors += author.getFullName();
            if (i < this.authors.size() - 1) {
                authors += ", ";
            }
        }
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookCopy getNextAvailableCopy() {
        Optional<BookCopy> optional
                = copies.stream()
                .filter(BookCopy::isAvailable)
                .findFirst();
        return optional.orElse(null);
    }

    public long getNumberOfAvailableCopies() {
        return copies.stream()
                .filter(BookCopy::isAvailable)
                .count();
    }

    public BookCopy getCopy(int copyNum) {
        for (BookCopy c : copies) {
            if (copyNum == c.getCopyNum()) {
                return c;
            }
        }
        return null;
    }

    public int getMaxCheckoutLength() {
        return maxCheckoutLength;
    }
}
