package com.eventora.platform.u202218233.shared.domain.model.valueObjects;

import java.util.regex.Pattern;

/**
 * Email Address value object.
 * Represents a valid email address with proper format validation.
 * This value object can be used across multiple bounded contexts within the
 * Eventora ecosystem.
 * 
 * @author [Tu nombre y apellidos]
 * 
 * @param address The email address string
 */
public record EmailAddress(String address) {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Creates an EmailAddress with validation.
     *
     * @param address The email address string
     * @throws IllegalArgumentException if the email format is invalid
     */
    public EmailAddress {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be null or empty");
        }

        String trimmedAddress = address.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(trimmedAddress).matches()) {
            throw new IllegalArgumentException("Invalid email address format: " + address);
        }

        address = trimmedAddress;
    }

    /**
     * Gets the email address as string.
     *
     * @return The email address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Validates if a string is a valid email format.
     *
     * @param email The email string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim().toLowerCase()).matches();
    }
}
