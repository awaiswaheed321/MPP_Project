package com.library.interfaces;

import com.library.classes.Book;
import com.library.classes.LibraryMember;
import com.library.classes.User;

import java.util.HashMap;


public interface DataAccess {
    HashMap<String, Book> readBooksMap();

    HashMap<String, User> readUserMap();

    HashMap<String, LibraryMember> readMemberMap();

    void saveNewMember(LibraryMember member);

    void saveBook(Book book);
}
