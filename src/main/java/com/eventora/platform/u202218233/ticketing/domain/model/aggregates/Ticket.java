package com.eventora.platform.u202218233.ticketing.domain.model.aggregates;

import com.eventora.platform.u202218233.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.eventora.platform.u202218233.shared.domain.model.valueObjects.EmailAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Ticket aggregate root.
 * Represents a ticket sold for a specific event in the Eventora platform.
 * Contains buyer information, event reference, pricing and purchase details.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends AuditableAbstractAggregateRoot<Ticket> {

    /**
     * The unique identifier of the event this ticket is for.
     * Must be a valid UUID version 4.
     */
    @Column(name = "event_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID eventId;

    /**
     * The name of the ticket buyer.
     * Cannot be null or empty.
     */
    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    /**
     * The email address of the ticket buyer.
     * Must be a valid email format.
     */
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "buyer_email", nullable = false))
    private EmailAddress buyerEmail;

    /**
     * The price paid for this ticket.
     * Must be positive.
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * The timestamp when the ticket was purchased.
     * Automatically set when the ticket is created.
     */
    @Column(name = "purchased_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedAt;

    /**
     * Creates a new Ticket instance.
     *
     * @param eventId    The UUID of the event
     * @param buyerName  The name of the buyer
     * @param buyerEmail The email address of the buyer
     * @param price      The ticket price
     */
    public Ticket(UUID eventId, String buyerName, EmailAddress buyerEmail, Double price) {
        this.eventId = eventId;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.price = price;
        this.purchasedAt = new Date();

        validateTicket();
    }

    /**
     * Creates a new Ticket instance with string email.
     *
     * @param eventId           The UUID of the event
     * @param buyerName         The name of the buyer
     * @param buyerEmailAddress The email address as string
     * @param price             The ticket price
     */
    public Ticket(UUID eventId, String buyerName, String buyerEmailAddress, Double price) {
        this(eventId, buyerName, new EmailAddress(buyerEmailAddress), price);
    }

    /**
     * Validates the ticket data.
     *
     * @throws IllegalArgumentException if any validation fails
     */
    private void validateTicket() {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        if (buyerName == null || buyerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }

        if (buyerEmail == null) {
            throw new IllegalArgumentException("Buyer email cannot be null");
        }

        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        if (purchasedAt != null && purchasedAt.after(new Date())) {
            throw new IllegalArgumentException("Purchase date cannot be in the future");
        }
    }

    /**
     * Gets the buyer email as string.
     *
     * @return The buyer email address
     */
    public String getBuyerEmailAddress() {
        return buyerEmail != null ? buyerEmail.address() : null;
    }

    /**
     * Checks if the ticket price matches a given amount.
     *
     * @param amount The amount to compare
     * @return true if prices match, false otherwise
     */
    public boolean priceMatches(Double amount) {
        return this.price != null && this.price.equals(amount);
    }

    /**
     * Gets the event ID as string.
     *
     * @return The event ID as string
     */
    public String getEventIdAsString() {
        return eventId != null ? eventId.toString() : null;
    }
}
