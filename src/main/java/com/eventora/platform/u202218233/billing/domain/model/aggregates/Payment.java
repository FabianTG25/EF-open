package com.eventora.platform.u202218233.billing.domain.model.aggregates;

import com.eventora.platform.u202218233.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends AuditableAbstractAggregateRoot<Payment> {


    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "card_hash", nullable = false)
    private String cardHash;

    @Column(name = "paid_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;

    public Payment(Long ticketId, Double amount, String cardHash) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.cardHash = cardHash;
        this.paidAt = new Date();

        validatePayment();
    }


    private void validatePayment() {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (cardHash == null || cardHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Card hash cannot be null or empty");
        }

        if (paidAt != null && paidAt.after(new Date())) {
            throw new IllegalArgumentException("Payment date cannot be in the future");
        }
    }

    public boolean amountMatches(Double expectedAmount) {
        return this.amount != null && this.amount.equals(expectedAmount);
    }

    public boolean isForTicket(Long ticketId) {
        return this.ticketId != null && this.ticketId.equals(ticketId);
    }
}
