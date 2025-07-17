package com.eventora.platform.u202218233.ticketing.interfaces.REST.resources;

import java.util.Date;

/**
 * Resource representing a ticket in HTTP responses.
 * Contains ticket information for API responses.
 */
public record TicketResource(
        Long id,
        String eventId,
        String buyerEmail,
        Double price,
        Date createdAt,
        Date updatedAt) {
}
