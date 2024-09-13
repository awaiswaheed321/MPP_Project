package com.library.services;

import com.library.classes.Author;
import com.library.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationService {

    private static final List<String> validStates = Arrays.asList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    );

    public static void validateBook(String isbn, String title, String copiesStr, List<Author> selectedAuthors, boolean isCheckoutSelected) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if (isbn == null || isbn.trim().isEmpty()) {
            errors.add("ISBN cannot be empty.");
        } else if(!isbn.matches("\\d{2}-\\d{4}")) {
            errors.add("Invalid ISBN format. Please enter in the format 12-1234.");
        }
        if (title == null || title.trim().isEmpty()) {
            errors.add("Title cannot be empty.");
        }
        try {
            int copies = Integer.parseInt(copiesStr);
            if (copies <= 0) {
                errors.add("Copies must be a number greater than 0.");
            }
        } catch (NumberFormatException e) {
            errors.add("Copies must be a valid number.");
        }
        if (selectedAuthors == null || selectedAuthors.isEmpty()) {
            errors.add("Please select at least one author.");
        }
        if (!isCheckoutSelected) {
            errors.add("Please select a checkout length.");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public static void validateMember(String memberId, String firstName, String lastName,
                                      String phoneNumber, String street, String city,
                                      String state, String zip) throws ValidationException {
        SystemController sc = new SystemController();
        List<String> errors = new ArrayList<>();
        if (memberId == null || memberId.trim().isEmpty()) {
            errors.add("Member ID is required.");
        }
        validateUserInfo(firstName, lastName, phoneNumber, street, city, state, zip, errors);

        if (sc.isValidMember(memberId)) {
            errors.add("The Member ID already exists.");
        }
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            errors.add("Invalid Phone Number. Please enter a 10-digit number.");
        }
        if (zip != null && !zip.matches("\\d{5}")) {
            errors.add("Invalid Zip Code. Please enter a 5-digit zip code.");
        }
        if (state != null && !state.matches("[A-Z]{2}") || !validStates.contains(state)) {
            errors.add("Invalid State. Please enter a 2-letter state code.");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public static void validateAuthor(String firstName, String lastName, String telephone,
                                      String street, String city, String state,
                                      String zip, String bio) throws ValidationException {
        List<String> errors = new ArrayList<>();
        validateUserInfo(firstName, lastName, telephone, street, city, state, zip, errors);
        if (bio == null || bio.trim().isEmpty()) {
            errors.add("Biography is required.");
        }
        if (telephone != null && !telephone.matches("\\d{10}")) {
            errors.add("Invalid Phone Number. Please enter a 10-digit number.");
        }
        if (zip != null && !zip.matches("\\d{5}")) {
            errors.add("Invalid Zip Code. Please enter a 5-digit zip code.");
        }
        if (state != null && (!state.matches("[A-Z]{2}") || !validStates.contains(state))) {
            errors.add("Invalid State. Please enter a 2-letter state code.");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private static void validateUserInfo(String firstName, String lastName, String telephone, String street, String city, String state, String zip, List<String> errors) {
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.add("First Name is required.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.add("Last Name is required.");
        }
        if (telephone == null || telephone.trim().isEmpty()) {
            errors.add("Phone Number is required.");
        }
        if (street == null || street.trim().isEmpty()) {
            errors.add("Street is required.");
        }
        if (city == null || city.trim().isEmpty()) {
            errors.add("City is required.");
        }
        if (state == null || state.trim().isEmpty()) {
            errors.add("State is required.");
        }
        if (zip == null || zip.trim().isEmpty()) {
            errors.add("Zip Code is required.");
        }
    }
}
