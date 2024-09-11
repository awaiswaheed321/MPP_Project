package com.library.interfaces;

import com.library.classes.Book;
import com.library.classes.CheckoutEntry;
import com.library.exceptions.LibrarySystemException;
import com.library.exceptions.LoginException;

import java.util.List;

public interface ControllerInterface {
    public void login(String id, String password) throws LoginException;

    public List<String> allMemberIds();

    public List<String> allBookIds();

    public boolean isValidMember(String memberId);

    public CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException;

    public List<Book> allBooks();

    public void saveBook(Book book);

}
