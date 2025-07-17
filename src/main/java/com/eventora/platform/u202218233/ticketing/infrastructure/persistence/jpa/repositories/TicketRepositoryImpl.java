package com.eventora.platform.u202218233.ticketing.infrastructure.persistence.jpa.repositories;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA implementation of the TicketRepository domain interface.
 * Provides concrete persistence operations using Spring Data JPA.
 * This implementation bridges the domain and infrastructure layers.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    /**
     * Constructor for TicketRepositoryImpl.
     *
     * @param jpaTicketRepository The JPA repository implementation
     */
    public TicketRepositoryImpl(JpaTicketRepository jpaTicketRepository) {
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return jpaTicketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long ticketId) {
        return jpaTicketRepository.findById(ticketId);
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return jpaTicketRepository.findByEventId(eventId);
    }

    @Override
    public List<Ticket> findByBuyerEmail(String buyerEmail) {
        return jpaTicketRepository.findByBuyerEmail(buyerEmail);
    }

    @Override
    public boolean existsByEventIdAndBuyerEmail(UUID eventId, String buyerEmail) {
        return jpaTicketRepository.existsByEventIdAndBuyerEmail(eventId, buyerEmail);
    }

    @Override
    public List<Ticket> findAll() {
        return jpaTicketRepository.findAll();
    }

    @Override
    public void deleteById(Long ticketId) {
        jpaTicketRepository.deleteById(ticketId);
    }

    @Override
    public Long countTicketsByEventId(UUID eventId) {
        return jpaTicketRepository.countTicketsByEventId(eventId);
    }
}
