package com.eventora.platform.u202218233.ticketing.domain.model.commands;

import java.util.UUID;

/**
 * Command to create a new ticket.
 * Contains all the necessary information to create a ticket for an event.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 * 
 * @param eventId The UUID of the event
 * @param buyerName The name of the buyer
 * @param buyerEmail The email address of the buyer
 * @param price The ticket price
 */
public record CreateTicketCommand(
    UUID eventId,
    String buyerName,
    String buyerEmail,
    Double price
) {
    /**
     * Creates a CreateTicketCommand with validation.
     *
     * @param eventId The UUID of the event
     * @param buyerName The name of the buyer
     * @param buyerEmail The email address of the buyer
     * @param price The ticket price
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public CreateTicketCommand {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (buyerName == null || buyerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }
        if (buyerEmail == null || buyerEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer email cannot be null or empty");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    /**
     * Creates a CreateTicketCommand from string eventId.
     *
     * @param eventIdString The event ID as string
     * @param buyerName The name of the buyer
     * @param buyerEmail The email address of the buyer
     * @param price The ticket price
     * @return CreateTicketCommand instance
     */
    public static CreateTicketCommand fromString(String eventIdString, String buyerName, String buyerEmail, Double price) {
        if (eventIdString == null || eventIdString.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID string cannot be null or empty");
        }
        
        try {
            UUID eventUuid = UUID.fromString(eventIdString.trim());
            return new CreateTicketCommand(eventUuid, buyerName, buyerEmail, price);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format for Event ID: " + eventIdString, e);
        }
    }
}
