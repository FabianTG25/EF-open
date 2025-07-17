package com.eventora.platform.u202218233.ticketing.interfaces.REST.resources;

/**
 * Resource for creating a new ticket.
 * Contains the data needed to create a ticket.
 */
public record CreateTicketResource(
        String eventId,
        String buyerName,
        String buyerEmail,
        Double price) {
    public CreateTicketResource {
        if (eventId == null || eventId.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        if (buyerName == null || buyerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }
        if (buyerEmail == null || buyerEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer email cannot be null or empty");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
