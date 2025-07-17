package com.eventora.platform.u202218233.billing.infrastructure.persistence.jpa.repositories;

import com.eventora.platform.u202218233.billing.domain.model.aggregates.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds a payment by ticket ID.
     *
     * @param ticketId The ticket ID to search for
     * @return Optional containing the payment if found, empty otherwise
     */
    Optional<Payment> findByTicketId(Long ticketId);

    /**
     * Checks if a payment exists for a given ticket ID.
     *
     * @param ticketId The ticket ID to check
     * @return true if a payment exists for the ticket, false otherwise
     */
    boolean existsByTicketId(Long ticketId);
}
