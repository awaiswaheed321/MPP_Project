package com.library.classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

final public class Author extends Person implements Serializable {
    private String bio;
    private String authorId;
    public String getBio() {
        return bio;
    }

    public Author(String firstName, String lastName, String telephone, Address a, String bio) {
        super(firstName, lastName, telephone, a);
        this.bio = bio;
        this.authorId = UUID.randomUUID().toString();
    }

    public String getAuthorId() {
        return authorId;
    }

    @Serial
    private static final long serialVersionUID = 7508481940058530471L;
}
