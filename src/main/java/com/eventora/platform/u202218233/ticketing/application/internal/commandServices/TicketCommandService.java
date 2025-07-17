package com.eventora.platform.u202218233.ticketing.application.internal.commandServices;

import com.eventora.platform.u202218233.shared.domain.model.valueObjects.EmailAddress;
import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.TicketRepository;
import com.eventora.platform.u202218233.ticketing.domain.model.commands.CreateTicketCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command service for ticket operations.
 * Handles all commands related to ticket management following CQRS pattern.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@Service
public class TicketCommandService {

    private final TicketRepository ticketRepository;

    /**
     * Constructor for TicketCommandService.
     *
     * @param ticketRepository The ticket repository
     */
    public TicketCommandService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Handles the creation of a new ticket.
     * Applies all business rules and validations before persisting.
     *
     * @param command The create ticket command
     * @return The created ticket
     * @throws IllegalArgumentException if validation fails
     */
    @Transactional
    public Ticket handle(CreateTicketCommand command) {
        // Validate command
        if (command == null) {
            throw new IllegalArgumentException("Create ticket command cannot be null");
        }

        // Create EmailAddress value object with validation
        EmailAddress buyerEmail = new EmailAddress(command.buyerEmail());

        // Create ticket aggregate
        Ticket ticket = new Ticket(
                command.eventId(),
                command.buyerName(),
                buyerEmail,
                command.price());

        // Persist and return the ticket
        return ticketRepository.save(ticket);
    }
}
