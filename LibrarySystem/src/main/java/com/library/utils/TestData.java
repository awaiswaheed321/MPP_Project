package com.library.utils;

import com.library.domain.*;
import com.library.enums.Auth;
import com.library.services.DataAccessFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class loads data into the data repository and also
 * sets up the storage units that are used in the application.
 * The main method in this class must be run once (and only
 * once) before the rest of the application can work properly.
 * It will create three serialized objects in the dataaccess.storage
 * folder.
 */
public class TestData {
    public static void main(String[] args) {
        TestData td = new TestData();
        td.bookData();
        td.libraryMemberData();
        td.userData();
        td.addAuthors();
        System.out.println("\n\n***********Test Data Generated. Added Authors, Books, Members and Users.***********\n\n");
    }

    private void addAuthors() {
        DataAccessFacade.saveAuthorsData(allAuthors);
    }

    ///create books
    public void bookData() {
        allBooks.get(0).addCopy();
        allBooks.get(0).addCopy();
        allBooks.get(1).addCopy();
        allBooks.get(3).addCopy();
        allBooks.get(2).addCopy();
        allBooks.get(2).addCopy();
        DataAccessFacade.saveBooksData(allBooks);
    }

    public void userData() {
        DataAccessFacade.saveUsersData(allUsers);
    }

    //create library members
    public void libraryMemberData() {
        LibraryMember libraryMember = new LibraryMember("1001", "Andy", "Rogers", "6412232211", addresses.get(4));
        members.add(libraryMember);
        libraryMember = new LibraryMember("1002", "Drew", "Stevens", "7029982414", addresses.get(5));
        members.add(libraryMember);

        libraryMember = new LibraryMember("1003", "Sarah", "Eagleton", "4512348811", addresses.get(6));
        members.add(libraryMember);

        libraryMember = new LibraryMember("1004", "Ricardo", "Montalbahn", "6414722871", addresses.get(7));
        members.add(libraryMember);

        DataAccessFacade.saveMembersData(members);
    }

    ///////////// DATA //////////////
    List<LibraryMember> members = new ArrayList<LibraryMember>();

    List<Address> addresses = new ArrayList<Address>() {
        {
            add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
            add(new Address("51 S. George", "Georgetown", "MI", "65434"));
            add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
            add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
            add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
            add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
            add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
            add(new Address("501 Central", "Mountain View", "CA", "94707"));
        }
    };


    public List<Author> allAuthors = new ArrayList<Author>() {
        {
            add(new Author("Joe", "Thomas", "6414452123", addresses.get(0), "A happy man is he.", "2001"));
            add(new Author("Sandra", "Thomas", "6414452123", addresses.get(0), "A happy wife is she.", "2002"));
            add(new Author("Nirmal", "Pugh", "6419193223", addresses.get(1), "Thinker of thoughts.", "2003"));
            add(new Author("Andrew", "Cleveland", "9764452232", addresses.get(2), "Author of children' books.", "2004"));
            add(new Author("Sarah", "Connor", "1234222663", addresses.get(3), "Known for her clever style.", "2005"));
        }
    };

    List<Book> allBooks = new ArrayList<Book>() {
        {
            add(new Book("23-11451", "The Great Escape", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
            add(new Book("28-12331", "Journey to the Ice", 7, Collections.singletonList(allAuthors.get(2))));
            add(new Book("99-22223", "Java for Experts", 21, Arrays.asList(allAuthors.get(3), allAuthors.get(1))));
            add(new Book("48-56882", "First Day Adventures", 7, Collections.singletonList(allAuthors.get(4))));
            add(new Book("23-11452", "The Ocean's Secret", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
            add(new Book("28-12333", "Frozen Expeditions", 7, Collections.singletonList(allAuthors.get(2))));
            add(new Book("99-22226", "Java Mastery", 21, Arrays.asList(allAuthors.get(3), allAuthors.get(4))));
            add(new Book("48-56888", "School Chronicles", 7, Collections.singletonList(allAuthors.get(4))));
            add(new Book("23-11478", "Mysteries of the Deep", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
            add(new Book("28-12334", "The Antarctic Trek", 7, Collections.singletonList(allAuthors.get(2))));
            add(new Book("99-22245", "Java Unleashed", 21, Collections.singletonList(allAuthors.get(3))));
            add(new Book("48-56892", "Adventures in School", 7, Collections.singletonList(allAuthors.get(4))));
        }
    };

    List<User> allUsers = new ArrayList<User>() {
        {
            add(new User("101", PasswordUtil.hashPassword("xyz"), Auth.LIBRARIAN));
            add(new User("102", PasswordUtil.hashPassword("abc"), Auth.ADMIN));
            add(new User("103", PasswordUtil.hashPassword("123"), Auth.BOTH));
        }
    };
}
