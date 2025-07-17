package com.eventora.platform.u202218233.billing.interfaces.REST;

import com.eventora.platform.u202218233.billing.application.internal.commandServices.PaymentCommandService;
import com.eventora.platform.u202218233.billing.domain.model.aggregates.Payment;
import com.eventora.platform.u202218233.billing.domain.model.commands.CreatePaymentCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * PaymentsController handles REST API requests for payment operations.
 * Provides endpoints for creating payments in the Eventora platform.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "Payment management endpoints")
public class PaymentsController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsController.class);
    private final PaymentCommandService paymentCommandService;

    public PaymentsController(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    /**
     * Creates a new payment for a ticket.
     *
     * @param createPaymentCommand The payment creation request
     * @return ResponseEntity with the created payment information
     */
    @PostMapping
    @Operation(summary = "Create a new payment", description = "Creates a new payment for an existing ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment data or business rule violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createPayment(@Valid @RequestBody CreatePaymentCommand createPaymentCommand) {
        try {
            logger.info("Creating payment for ticket ID: {}, amount: {}",
                    createPaymentCommand.ticketId(), createPaymentCommand.amount());

            Payment payment = paymentCommandService.handle(createPaymentCommand);
            logger.info("Payment created successfully with ID: {}", payment.getId());

            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Bad request for payment creation: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Bad Request", "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal server error while creating payment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }
}
