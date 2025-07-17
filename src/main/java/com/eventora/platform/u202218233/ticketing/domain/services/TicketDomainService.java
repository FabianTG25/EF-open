package com.eventora.platform.u202218233.ticketing.domain.services;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;

/**
 * Domain service for ticket validation.
 * Contains business logic that doesn't belong to any specific aggregate.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
public class TicketDomainService {

    /**
     * Validates if a ticket can be created with the given parameters.
     * Applies all business rules for ticket creation.
     *
     * @param ticket The ticket to validate
     * @throws IllegalArgumentException if validation fails
     */
    public void validateTicketCreation(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }

        // Business rule: Ticket must have a valid event ID
        if (ticket.getEventId() == null) {
            throw new IllegalArgumentException("Ticket must be associated with a valid event");
        }

        // Business rule: Buyer information must be complete
        if (ticket.getBuyerName() == null || ticket.getBuyerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name is required");
        }

        if (ticket.getBuyerEmail() == null) {
            throw new IllegalArgumentException("Buyer email is required");
        }

        // Business rule: Price must be positive
        if (ticket.getPrice() == null || ticket.getPrice() <= 0) {
            throw new IllegalArgumentException("Ticket price must be positive");
        }

        // Business rule: Purchase date cannot be in the future
        if (ticket.getPurchasedAt() != null && ticket.getPurchasedAt().after(new java.util.Date())) {
            throw new IllegalArgumentException("Purchase date cannot be in the future");
        }
    }

    /**
     * Validates if a ticket price is within acceptable business limits.
     *
     * @param price The price to validate
     * @return true if price is valid for business rules
     */
    public boolean isValidTicketPrice(Double price) {
        if (price == null) {
            return false;
        }

        // Business rule: Price must be positive and reasonable
        // Could include upper limit validation if required by business
        return price > 0 && price <= 10000.0; // Maximum ticket price of $10,000
    }

    /**
     * Validates if the buyer information is complete and valid.
     *
     * @param buyerName  The buyer name
     * @param buyerEmail The buyer email
     * @return true if buyer information is valid
     */
    public boolean isValidBuyerInformation(String buyerName, String buyerEmail) {
        return buyerName != null &&
                !buyerName.trim().isEmpty() &&
                buyerEmail != null &&
                !buyerEmail.trim().isEmpty();
    }
}
