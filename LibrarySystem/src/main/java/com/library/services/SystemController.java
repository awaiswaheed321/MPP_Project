package com.library.services;

import com.library.classes.*;
import com.library.enums.Auth;
import com.library.exceptions.LibrarySystemException;
import com.library.exceptions.LoginException;
import com.library.interfaces.ControllerInterface;
import com.library.interfaces.DataAccess;
import com.library.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;
    DataAccess da = DataAccessFacade.getInstance();

    @Override
    public void login(String id, String password) throws LoginException {
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!PasswordUtil.checkPassword(password, passwordFound)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(da.readBooksMap().values());
    }

    @Override
    public boolean isValidMember(String memberId) {
        MemberService ms = new MemberService();
        return ms.isValidMember(memberId);
    }

    @Override
    public List<Book> allBooks() {
        return new ArrayList<>(da.readBooksMap().values());
    }

    @Override
    public void saveBook(Book book) {
        da.saveBook(book);
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>(da.readAuthorMap().values());
        authors.sort((a1, a2) -> {
            int firstNameComparison = a1.getFirstName().compareToIgnoreCase(a2.getFirstName());
            if (firstNameComparison != 0) {
                return firstNameComparison;
            }
            return a1.getLastName().compareToIgnoreCase(a2.getLastName());
        });
        return authors;
    }

    @Override
    public void saveNewBook(Book book) {
        da.saveNewBook(book);
    }

    @Override
    public List<LibraryMember> getAllMembers() {
        List<LibraryMember> libraryMembers = new ArrayList<>(da.readMemberMap().values());
        libraryMembers.sort((a1, a2) -> {
            int firstNameComparison = a1.getFirstName().compareToIgnoreCase(a2.getFirstName());
            if (firstNameComparison != 0) {
                return firstNameComparison;
            }
            return a1.getLastName().compareToIgnoreCase(a2.getLastName());
        });
        return libraryMembers;
    }

    @Override
    public CheckoutEntry checkout(String memberId, String isbn) throws LibrarySystemException {
        return new BookService().checkout(memberId, isbn);
    }
}
