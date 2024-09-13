package com.library.interfaces;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.CheckoutEntry;
import com.library.domain.LibraryMember;
import com.library.exceptions.LibrarySystemException;
import com.library.exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {
    void login(String id, String password) throws LoginException;

    List<Book> getAllBooks();

    boolean isValidMember(String memberId);

    CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException;

    List<Book> allBooks();

    void saveBook(Book book);

    List<Author> getAllAuthors();

    void saveNewBook(Book book);

    List<LibraryMember> getAllMembers();
}
