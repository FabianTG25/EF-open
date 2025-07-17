package com.eventora.platform.u202218233.ticketing.interfaces.REST.transform;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import com.eventora.platform.u202218233.ticketing.domain.model.commands.CreateTicketCommand;
import com.eventora.platform.u202218233.ticketing.interfaces.REST.resources.CreateTicketResource;
import com.eventora.platform.u202218233.ticketing.interfaces.REST.resources.TicketResource;

/**
 * Assembler class to transform between Ticket domain objects and REST
 * resources.
 */
public class TicketResourceFromEntityAssembler {

    /**
     * Transforms a Ticket domain object to a TicketResource.
     *
     * @param entity The ticket domain object
     * @return TicketResource for HTTP response
     */
    public static TicketResource toResourceFromEntity(Ticket entity) {
        if (entity == null) {
            return null;
        }

        return new TicketResource(
                entity.getId(),
                entity.getEventId().toString(),
                entity.getBuyerEmail().address(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Transforms a CreateTicketResource to a CreateTicketCommand.
     *
     * @param resource The create ticket resource
     * @return CreateTicketCommand for the application layer
     */
    public static CreateTicketCommand toCommandFromResource(CreateTicketResource resource) {
        if (resource == null) {
            return null;
        }

        return new CreateTicketCommand(
                java.util.UUID.fromString(resource.eventId()),
                resource.buyerName(),
                resource.buyerEmail(),
                resource.price());
    }

    /**
     * Validates a CreateTicketResource.
     *
     * @param resource The create ticket resource to validate
     * @throws IllegalArgumentException if validation fails
     */
    public static void validateCreateTicketResource(CreateTicketResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Create ticket resource cannot be null");
        }

        if (resource.eventId() == null || resource.eventId().trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }

        if (resource.buyerName() == null || resource.buyerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }

        if (resource.buyerEmail() == null || resource.buyerEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Buyer email cannot be null or empty");
        }

        if (resource.price() == null || resource.price() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        // Validate UUID format
        try {
            java.util.UUID.fromString(resource.eventId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Event ID must be a valid UUID format");
        }
    }

    /**
     * Transforms a list of Ticket domain objects to a list of TicketResources.
     * This method is kept for potential future use.
     *
     * @param tickets The list of ticket domain objects
     * @return List of TicketResource for HTTP response
     */
    public static java.util.List<TicketResource> toResourceFromEntityList(java.util.List<Ticket> tickets) {
        if (tickets == null) {
            return java.util.List.of();
        }

        return tickets.stream()
                .map(TicketResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
