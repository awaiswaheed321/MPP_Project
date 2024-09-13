package com.library.exceptions;

public class DuplicateBookCopyCheckoutException extends Exception {
    public DuplicateBookCopyCheckoutException(String message) {
        super(message);
    }
}