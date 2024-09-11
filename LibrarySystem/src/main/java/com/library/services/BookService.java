package com.library.services;

import com.library.classes.Book;
import com.library.classes.BookCopy;
import com.library.classes.CheckoutEntry;
import com.library.classes.LibraryMember;
import com.library.exceptions.BookCopyNotAvailableException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.LibrarySystemException;
import com.library.interfaces.DataAccess;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

public class BookService {
    DataAccess da = DataAccessFacade.getInstance();

    private Collection<Book> allBooks() {
        HashMap<String, Book> allBookMaps = da.readBooksMap();
        Collection<Book> books = allBookMaps.values();
        return books;
    }

    public CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException {
        LibraryMember member = new MemberService().getMember(memberId);
        BookCopy bookCopy = getBookCopy(isbn);
        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(bookCopy.getBook().getMaxCheckoutLength());
        CheckoutEntry checkout = new CheckoutEntry(member, bookCopy, now, dueDate);
        bookCopy.changeAvailability();
        member.addCheckout(checkout);
        da.saveBook(bookCopy.getBook());
        da.saveNewMember(member);
        return checkout;
    }

    private BookCopy getBookCopy(String isbn) throws LibrarySystemException {
        Book theBook = null;
        for (Book book : allBooks()) {
            if (book.getIsbn().equals(isbn)) {
                theBook = book;
                break;
            }
        }

        if (theBook == null) throw new BookNotFoundException();

        BookCopy bookCopy = theBook.getNextAvailableCopy();
        if (bookCopy == null) throw new BookCopyNotAvailableException();
        return bookCopy;
    }
}
