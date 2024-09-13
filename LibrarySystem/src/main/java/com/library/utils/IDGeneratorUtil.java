package com.library.utils;

import com.library.classes.Author;
import com.library.classes.LibraryMember;
import com.library.interfaces.DataAccess;
import com.library.services.DataAccessFacade;

import java.util.Map;
import java.util.OptionalInt;

public class IDGeneratorUtil {
    private static String MEMBER_ID = "0";
    private static String AUTHOR_ID = "0";

    public static void setStartingId() {
        DataAccess da = new DataAccessFacade();
        setMemberId(da);
        setAuthorId(da);
    }

    private static void setMemberId(DataAccess da) {
        Map<String, LibraryMember> memberMap = da.readMemberMap();
        OptionalInt maxId = memberMap.keySet().stream()
                .mapToInt(Integer::parseInt) // Convert each String to an int
                .max();
        MEMBER_ID = String.valueOf(maxId.orElseThrow(() -> new IllegalArgumentException("Member list is empty.")));
    }

    private static void setAuthorId(DataAccess da) {
        Map<String, Author> authorMap = da.readAuthorMap();
        OptionalInt maxId = authorMap.keySet().stream()
                .mapToInt(Integer::parseInt) // Convert each String to an int
                .max();
        AUTHOR_ID = String.valueOf(maxId.orElseThrow(() -> new IllegalArgumentException("Author list is empty.")));
    }

    public static String getNextMemberId() {
        MEMBER_ID = String.valueOf(Integer.parseInt(MEMBER_ID) + 1);
        return MEMBER_ID;
    }

    public static String getNextAuthorId() {
        AUTHOR_ID = String.valueOf(Integer.parseInt(AUTHOR_ID) + 1);
        return AUTHOR_ID;
    }
}
