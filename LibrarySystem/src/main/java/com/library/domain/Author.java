package com.library.domain;

import java.io.Serial;
import java.io.Serializable;

final public class Author extends Person implements Serializable {
    private final String bio;
    private final String authorId;

    public String getBio() {
        return bio;
    }

    public Author(String firstName, String lastName, String telephone, Address a, String bio, String authorId) {
        super(firstName, lastName, telephone, a);
        this.bio = bio;
        this.authorId = authorId;
    }

    public String getAuthorId() {
        return authorId;
    }

    @Serial
    private static final long serialVersionUID = 7508481940058530471L;
}
