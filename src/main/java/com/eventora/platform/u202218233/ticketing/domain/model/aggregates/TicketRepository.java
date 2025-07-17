package com.eventora.platform.u202218233.ticketing.domain.model.aggregates;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {

    /**
     * Saves a ticket to the repository.
     *
     * @param ticket The ticket to save
     * @return The saved ticket
     */
    Ticket save(Ticket ticket);

    /**
     * Finds a ticket by its ID.
     *
     * @param ticketId The ticket ID
     * @return Optional containing the ticket if found
     */
    Optional<Ticket> findById(Long ticketId);

    /**
     * Finds all tickets for a specific event.
     *
     * @param eventId The event UUID
     * @return List of tickets for the event
     */
    List<Ticket> findByEventId(UUID eventId);

    /**
     * Finds tickets by buyer email.
     *
     * @param buyerEmail The buyer email address
     * @return List of tickets for the buyer
     */
    List<Ticket> findByBuyerEmail(String buyerEmail);

    /**
     * Checks if a ticket exists for a given event and buyer email.
     *
     * @param eventId    The event UUID
     * @param buyerEmail The buyer email
     * @return true if ticket exists, false otherwise
     */
    boolean existsByEventIdAndBuyerEmail(UUID eventId, String buyerEmail);

    /**
     * Finds all tickets.
     *
     * @return List of all tickets
     */
    List<Ticket> findAll();

    /**
     * Deletes a ticket by its ID.
     *
     * @param ticketId The ticket ID
     */
    void deleteById(Long ticketId);

    /**
     * Counts the total number of tickets sold for an event.
     *
     * @param eventId The event UUID
     * @return Number of tickets sold
     */
    Long countTicketsByEventId(UUID eventId);
}
