package com.eventora.platform.u202218233.billing.domain.model.aggregates;

import java.util.Optional;


public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByTicketId(Long ticketId);

    boolean existsByTicketId(Long ticketId);
}
