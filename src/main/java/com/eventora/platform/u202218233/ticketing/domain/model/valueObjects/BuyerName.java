package com.eventora.platform.u202218233.ticketing.domain.model.valueObjects;

/**
 * Buyer Name value object.
 * Represents the name of a ticket buyer with validation.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 * 
 * @param value The buyer name
 */
public record BuyerName(String value) {

    /**
     * Creates a BuyerName with validation.
     *
     * @param value The buyer name
     * @throws IllegalArgumentException if value is null or empty
     */
    public BuyerName {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }
        value = value.trim();
    }

    /**
     * Gets the buyer name value.
     *
     * @return The buyer name as string
     */
    public String getValue() {
        return value;
    }
}
