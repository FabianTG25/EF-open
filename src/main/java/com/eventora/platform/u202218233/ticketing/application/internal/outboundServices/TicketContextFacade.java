package com.eventora.platform.u202218233.ticketing.application.internal.outboundServices;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;

import java.util.Optional;

/**
 * Outbound service interface for Anti-Corruption Layer (ACL).
 * Defines the contract for external bounded contexts to access ticket
 * information.
 * This interface is used by other bounded contexts (like billing) to retrieve
 * ticket data.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
public interface TicketContextFacade {

    /**
     * Retrieves a ticket by its ID for external bounded contexts.
     *
     * @param ticketId The ticket ID
     * @return Optional containing the ticket if found
     */
    Optional<Ticket> getTicketById(Long ticketId);

    /**
     * Checks if a ticket exists and retrieves its price for payment validation.
     *
     * @param ticketId The ticket ID
     * @return Optional containing the ticket price if ticket exists
     */
    Optional<Double> getTicketPrice(Long ticketId);

    /**
     * Validates if a ticket exists and can receive a payment.
     *
     * @param ticketId The ticket ID
     * @return true if ticket exists and is valid for payment, false otherwise
     */
    boolean canTicketReceivePayment(Long ticketId);

    /**
     * Gets ticket information in a format suitable for external contexts.
     *
     * @param ticketId The ticket ID
     * @return Optional containing ticket data for external use
     */
    Optional<TicketSummary> getTicketSummary(Long ticketId);

    /**
     * Record representing ticket summary for external bounded contexts.
     * Contains only the essential information needed by other contexts.
     *
     * @param ticketId   The ticket ID
     * @param eventId    The event ID
     * @param price      The ticket price
     * @param buyerEmail The buyer email
     * @param isValid    Whether the ticket is valid for operations
     */
    record TicketSummary(
            Long ticketId,
            String eventId,
            Double price,
            String buyerEmail,
            boolean isValid) {
    }
}
