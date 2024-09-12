package com.library.services;

import com.library.classes.*;
import com.library.interfaces.DataAccess;
import com.library.utils.DataUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;


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
        BOOKS, MEMBERS, USERS, AUTHORS, ADDRESSES;
    }


    public static final String OUTPUT_DIR = DataUtils.buildPath(System.getProperty("user.dir"), "LibrarySystem", "src", "main", "resources", "storage");

    public static final String DATE_PATTERN = "MM/dd/yyyy";


    // implement: other save operations
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
        // Returns a Map with name/value pairs being
        // isbn -> Book
        return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        // Returns a Map with name/value pairs being
        // memberId -> LibraryMember
        return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        // Returns a Map with name/value pairs being
        // userId -> User
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Author> readAuthorMap() {
        // Returns a Map with name/value pairs being
        // userId -> User
        return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Address> readAddressMap() {
        // Returns a Map with name/value pairs being
        // userId -> User
        return (HashMap<String, Address>) readFromStorage(StorageType.ADDRESSES);
    }

    ///// load methods - these place test data into the storage area
    ///// - used just once at startup

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

    final static class Pair<S, T> implements Serializable {
        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null)
                return false;
            if (this == ob)
                return true;
            if (ob.getClass() != getClass())
                return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }

        private static final long serialVersionUID = 5399827794066637059L;
    }
}
