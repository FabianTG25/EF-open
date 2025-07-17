package com.eventora.platform.u202218233.billing.infrastructure.persistence.jpa.repositories;

import com.eventora.platform.u202218233.billing.domain.model.aggregates.Payment;
import com.eventora.platform.u202218233.billing.domain.model.aggregates.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    public PaymentRepositoryImpl(JpaPaymentRepository jpaPaymentRepository) {
        this.jpaPaymentRepository = jpaPaymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return jpaPaymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> findByTicketId(Long ticketId) {
        return jpaPaymentRepository.findByTicketId(ticketId);
    }

    @Override
    public boolean existsByTicketId(Long ticketId) {
        return jpaPaymentRepository.existsByTicketId(ticketId);
    }
}
