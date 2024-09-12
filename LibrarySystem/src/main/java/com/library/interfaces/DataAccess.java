package com.library.interfaces;

import com.library.classes.*;

import java.util.HashMap;


public interface DataAccess {
    HashMap<String, Book> readBooksMap();

    HashMap<String, User> readUserMap();

    HashMap<String, LibraryMember> readMemberMap();

    HashMap<String, Author> readAuthorMap();

    void saveNewMember(LibraryMember member);

    void saveBook(Book book);

    void saveNewAuthor(Author author);
}
