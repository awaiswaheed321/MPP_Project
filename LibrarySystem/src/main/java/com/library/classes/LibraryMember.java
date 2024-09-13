package com.library.classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
    @Serial
    private static final long serialVersionUID = -2226197306790714013L;

    private final String memberId;

    private final List<CheckoutEntry> checkouts;

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
        this.checkouts = new ArrayList<>();
    }

    public void addCheckout(CheckoutEntry checkout) {
        checkouts.add(checkout);
    }

    public String getMemberId() {
        return memberId;
    }

    public List<CheckoutEntry> getCheckouts() {
        return checkouts;
    }

    @Override
    public String toString() {
        return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
                ", " + getTelephone() + " " + getAddress();
    }
}
