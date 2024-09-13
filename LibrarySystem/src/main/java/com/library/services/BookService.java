package com.library.services;

import com.library.domain.Book;
import com.library.domain.BookCopy;
import com.library.domain.CheckoutEntry;
import com.library.domain.LibraryMember;
import com.library.exceptions.*;
import com.library.interfaces.DataAccess;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

public class BookService {
    DataAccess da = DataAccessFacade.getInstance();

    private Collection<Book> allBooks() {
        HashMap<String, Book> allBookMaps = da.readBooksMap();
        return allBookMaps.values();
    }

    public CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException, DuplicateBookCopyCheckoutException {
        LibraryMember member = new MemberService().getMember(memberId);
        checkDuplicateCheckout(member, isbn);
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

    private void checkDuplicateCheckout(LibraryMember member, String isbn) throws DuplicateBookCopyCheckoutException {
        boolean bookAlreadyCheckedOut = member.getCheckouts().stream()
                .map(checkout -> checkout.getBookCopy().getBook().getIsbn())
                .anyMatch(isbn::equals);

        if (bookAlreadyCheckedOut) {
            throw new DuplicateBookCopyCheckoutException("A member cannot check out multiple copies of the same book.");
        }
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
