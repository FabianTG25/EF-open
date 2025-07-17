package com.eventora.platform.u202218233.billing.infrastructure.persistence.jpa.repositories;

import com.eventora.platform.u202218233.billing.application.internal.outboundServices.acl.TicketingContextFacade;
import com.eventora.platform.u202218233.ticketing.infrastructure.persistence.jpa.repositories.JpaTicketRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TicketingContextFacadeImpl implements TicketingContextFacade {

    private final JpaTicketRepository jpaTicketRepository;

    public TicketingContextFacadeImpl(JpaTicketRepository jpaTicketRepository) {
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public boolean existsTicketById(Long ticketId) {
        return jpaTicketRepository.existsById(ticketId);
    }

    @Override
    public Optional<Double> getTicketPrice(Long ticketId) {
        return jpaTicketRepository.findById(ticketId)
                .map(ticket -> ticket.getPrice());
    }
}
