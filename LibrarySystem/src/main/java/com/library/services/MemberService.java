package com.library.services;

import com.library.classes.LibraryMember;
import com.library.exceptions.LibraryMemberNotFoundException;
import com.library.interfaces.DataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberService {
    DataAccess da = DataAccessFacade.getInstance();

    private List<String> allMemberIds() {
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    public boolean isValidMember(String memberId) {
        for (String id : allMemberIds()) {
            if (memberId.equals(id)) return true;
        }

        return false;
    }

    public LibraryMember getMember(String memberId) throws LibraryMemberNotFoundException {
        Collection<LibraryMember> allMembers = da.readMemberMap().values();
        for (LibraryMember member : allMembers) {
            if (member.getMemberId().equals(memberId)) return member;
        }
        throw new LibraryMemberNotFoundException();
    }
}
