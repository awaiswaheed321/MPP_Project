package com.test.dataaccess;

import com.test.business.Book;
import com.test.business.LibraryMember;

import java.util.HashMap;


public interface DataAccess {
    public HashMap<String, Book> readBooksMap();

    public HashMap<String, User> readUserMap();

    public HashMap<String, LibraryMember> readMemberMap();

    public void saveNewMember(LibraryMember member);
}
