package com.library.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
    private String memberId;
    private List<CheckoutEntry> checkouts;

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
        this.checkouts = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
                ", " + getTelephone() + " " + getAddress();
    }

    public List<CheckoutEntry> getCheckouts() {
        return checkouts;
    }

    public void addCheckout(CheckoutEntry checkout) {
        if (checkouts == null) {
            checkouts = new ArrayList<>();
        }
        checkouts.add(checkout);
    }

    private static final long serialVersionUID = -2226197306790714013L;
}
