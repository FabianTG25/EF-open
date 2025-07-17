package com.eventora.platform.u202218233.ticketing.application.internal.outboundServices;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of the Ticket Context Facade for Anti-Corruption Layer (ACL).
 * Provides a controlled interface for external bounded contexts to access
 * ticket information.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@Service
@Transactional(readOnly = true)
public class TicketContextFacadeImpl implements TicketContextFacade {

    private final TicketRepository ticketRepository;

    /**
     * Constructor for TicketContextFacadeImpl.
     *
     * @param ticketRepository The ticket repository
     */
    public TicketContextFacadeImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Ticket> getTicketById(Long ticketId) {
        if (ticketId == null || ticketId <= 0) {
            return Optional.empty();
        }
        return ticketRepository.findById(ticketId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Double> getTicketPrice(Long ticketId) {
        return getTicketById(ticketId)
                .map(Ticket::getPrice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canTicketReceivePayment(Long ticketId) {
        return getTicketById(ticketId)
                .map(ticket -> ticket.getPrice() != null && ticket.getPrice() > 0)
                .orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TicketSummary> getTicketSummary(Long ticketId) {
        return getTicketById(ticketId)
                .map(ticket -> new TicketSummary(
                        ticket.getId(),
                        ticket.getEventIdAsString(),
                        ticket.getPrice(),
                        ticket.getBuyerEmailAddress(),
                        ticket.getPrice() != null && ticket.getPrice() > 0));
    }
}
