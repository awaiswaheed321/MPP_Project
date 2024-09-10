package com.library.interfaces;

import com.library.classes.Book;
import com.library.classes.LibraryMember;
import com.library.classes.User;

import java.util.HashMap;


public interface DataAccess {
    public HashMap<String, Book> readBooksMap();

    public HashMap<String, User> readUserMap();

    public HashMap<String, LibraryMember> readMemberMap();

    public void saveNewMember(LibraryMember member);
}
