package com.library.interfaces;

import com.library.classes.Address;
import com.library.classes.Author;
import com.library.classes.Book;
import com.library.classes.CheckoutEntry;
import com.library.exceptions.LibrarySystemException;
import com.library.exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {
    void login(String id, String password) throws LoginException;

    List<String> allMemberIds();

    List<Book> getAllBooks();

    boolean isValidMember(String memberId);

    CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException;

    List<Book> allBooks();

    void saveBook(Book book);

    List<Author> getAllAuthors();

    List<Address> getAllAddress();
}
