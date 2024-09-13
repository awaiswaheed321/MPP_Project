package com.library.services;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.LibraryMember;
import com.library.domain.User;
import com.library.interfaces.DataAccess;
import com.library.utils.DataUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataAccessFacade implements DataAccess {

    private static DataAccessFacade instance;

    public DataAccessFacade() {
    }

    public static DataAccessFacade getInstance() {
        if (instance == null) {
            instance = new DataAccessFacade();
        }
        return instance;
    }

    enum StorageType {
        BOOKS, MEMBERS, USERS, AUTHORS;
    }

    public static final String OUTPUT_DIR = DataUtils.buildPath(System.getProperty("user.dir"), "LibrarySystem", "src", "main", "resources", "storage");

    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);
    }

    @Override
    public void saveBook(Book book) {
        HashMap<String, Book> books = readBooksMap();
        books.replace(book.getIsbn(), book);
        saveToStorage(StorageType.BOOKS, books);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Author> readAuthorMap() {
        return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
    }

    public static void saveBooksData(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(StorageType.BOOKS, books);
    }

    public static void saveUsersData(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    public static void saveMembersData(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }

    public static void saveAuthorsData(List<Author> authorsList) {
        HashMap<String, Author> authors = new HashMap<>();
        authorsList.forEach(author -> authors.put(author.getAuthorId(), author));
        saveToStorage(StorageType.AUTHORS, authors);
    }

    @Override
    public void saveNewAuthor(Author author) {
        HashMap<String, Author> authors = readAuthorMap();
        String id = author.getAuthorId();
        authors.put(id, author);
        saveToStorage(StorageType.AUTHORS, authors);
    }

    @Override
    public void saveNewBook(Book book) {
        Map<String, Book> books = readBooksMap();
        books.put(book.getIsbn(), book);
        saveToStorage(StorageType.BOOKS, books);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            System.out.println("Path: " + path.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }
}
