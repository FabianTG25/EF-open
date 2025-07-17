package com.eventora.platform.u202218233.billing.application.internal.commandServices;

import com.eventora.platform.u202218233.billing.application.internal.outboundServices.acl.TicketingContextFacade;
import com.eventora.platform.u202218233.billing.domain.model.aggregates.Payment;
import com.eventora.platform.u202218233.billing.domain.model.aggregates.PaymentRepository;
import com.eventora.platform.u202218233.billing.domain.model.commands.CreatePaymentCommand;
import com.eventora.platform.u202218233.billing.domain.services.HashingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final HashingService hashingService;
    private final TicketingContextFacade ticketingContextFacade;

    public PaymentCommandService(
            PaymentRepository paymentRepository,
            HashingService hashingService,
            TicketingContextFacade ticketingContextFacade) {
        this.paymentRepository = paymentRepository;
        this.hashingService = hashingService;
        this.ticketingContextFacade = ticketingContextFacade;
    }

    @Transactional
    public Payment handle(CreatePaymentCommand command) {
        // Validate that ticket exists
        if (!ticketingContextFacade.existsTicketById(command.ticketId())) {
            throw new IllegalArgumentException("Ticket with ID " + command.ticketId() + " does not exist");
        }

        // Validate that no payment already exists for this ticket
        if (paymentRepository.existsByTicketId(command.ticketId())) {
            throw new IllegalArgumentException("Payment already exists for ticket ID " + command.ticketId());
        }

        // Get ticket price for validation
        Double ticketPrice = ticketingContextFacade.getTicketPrice(command.ticketId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Could not retrieve price for ticket ID " + command.ticketId()));

        // Validate that amount matches ticket price exactly
        if (!ticketPrice.equals(command.amount())) {
            throw new IllegalArgumentException(
                    String.format("Payment amount %.2f does not match ticket price %.2f",
                            command.amount(), ticketPrice));
        }

        // Generate secure hash for card number
        String cardHash = hashingService.hashCardNumber(command.cardNumber());

        // Create and save payment
        Payment payment = new Payment(command.ticketId(), command.amount(), cardHash);

        return paymentRepository.save(payment);
    }
}
