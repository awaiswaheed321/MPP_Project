package com.library.exceptions;

import java.io.Serial;
import java.util.List;

public class ValidationException extends Exception {
    @Serial
    private static final long serialVersionUID = 5500389132163337874L;
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}