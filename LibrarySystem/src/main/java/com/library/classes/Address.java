package com.library.classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/* Immutable */
final public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = -891229800414574888L;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String id;

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.id = UUID.randomUUID().toString();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "(" + street + ", " + city + ", " + zip + ")";
    }
}
