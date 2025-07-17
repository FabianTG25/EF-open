package com.eventora.platform.u202218233.ticketing.domain.model.valueObjects;

/**
 * Ticket Price value object.
 * Represents a monetary amount for a ticket with validation.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 * 
 * @param value The price value
 */
public record TicketPrice(Double value) {

    /**
     * Creates a TicketPrice with validation.
     *
     * @param value The price value
     * @throws IllegalArgumentException if value is null or not positive
     */
    public TicketPrice {
        if (value == null) {
            throw new IllegalArgumentException("Ticket price cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Ticket price must be positive");
        }
    }

    /**
     * Checks if this price equals another price.
     *
     * @param other The other price to compare
     * @return true if prices are equal, false otherwise
     */
    public boolean equals(TicketPrice other) {
        return other != null && this.value.equals(other.value);
    }

    /**
     * Checks if this price equals a Double value.
     *
     * @param other The Double value to compare
     * @return true if prices are equal, false otherwise
     */
    public boolean equals(Double other) {
        return other != null && this.value.equals(other);
    }

    /**
     * Gets the price value.
     *
     * @return The price as Double
     */
    public Double getValue() {
        return value;
    }
}
