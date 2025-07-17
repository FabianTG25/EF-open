package com.eventora.platform.u202218233.billing.domain.model.commands;

public record CreatePaymentCommand(
        Long ticketId,
        Double amount,
        String cardNumber) {
            
    public CreatePaymentCommand {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
    }
}
