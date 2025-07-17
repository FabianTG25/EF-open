package com.eventora.platform.u202218233.ticketing.domain.model.valueObjects;

import java.util.UUID;

/**
 * Event ID value object.
 * Represents a unique identifier for an event using UUID version 4.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 * 
 * @param value The UUID value
 */
public record EventId(UUID value) {

    /**
     * Creates an EventId with validation.
     *
     * @param value The UUID value
     * @throws IllegalArgumentException if value is null
     */
    public EventId {
        if (value == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
    }

    /**
     * Creates an EventId from a string.
     *
     * @param eventIdString The UUID as string
     * @return EventId instance
     * @throws IllegalArgumentException if string is invalid UUID
     */
    public static EventId fromString(String eventIdString) {
        if (eventIdString == null || eventIdString.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID string cannot be null or empty");
        }

        try {
            UUID uuid = UUID.fromString(eventIdString.trim());
            return new EventId(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format for Event ID: " + eventIdString, e);
        }
    }

    /**
     * Gets the UUID as string.
     *
     * @return The UUID as string
     */
    public String asString() {
        return value.toString();
    }

    /**
     * Gets the UUID value.
     *
     * @return The UUID value
     */
    public UUID getValue() {
        return value;
    }
}
