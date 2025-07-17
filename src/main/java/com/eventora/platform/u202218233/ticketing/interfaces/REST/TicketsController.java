package com.eventora.platform.u202218233.ticketing.interfaces.REST;

import com.eventora.platform.u202218233.ticketing.application.internal.commandServices.TicketCommandService;
import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import com.eventora.platform.u202218233.ticketing.domain.model.commands.CreateTicketCommand;
import com.eventora.platform.u202218233.ticketing.interfaces.REST.resources.CreateTicketResource;
import com.eventora.platform.u202218233.ticketing.interfaces.REST.resources.TicketResource;
import com.eventora.platform.u202218233.ticketing.interfaces.REST.transform.TicketResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for ticket operations.
 * Provides endpoints for ticket management following RESTful API principles.
 * 
 * @author [Tu nombre y apellidos]
 */
@RestController
@RequestMapping(value = "/api/v1/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tickets", description = "Ticket management operations")
public class TicketsController {

    private final TicketCommandService ticketCommandService;

    /**
     * Constructor for TicketsController.
     *
     * @param ticketCommandService The ticket command service
     */
    public TicketsController(TicketCommandService ticketCommandService) {
        this.ticketCommandService = ticketCommandService;
    }

    /**
     * Creates a new ticket.
     * Endpoint: POST /api/v1/tickets
     *
     * @param createTicketResource The ticket creation resource
     * @return ResponseEntity with the created ticket resource
     */
    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Creates a new ticket for an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TicketResource> createTicket(@RequestBody CreateTicketResource createTicketResource) {
        try {
            // Validate the resource
            TicketResourceFromEntityAssembler.validateCreateTicketResource(createTicketResource);

            // Transform resource to command
            CreateTicketCommand command = TicketResourceFromEntityAssembler.toCommandFromResource(createTicketResource);

            // Execute command
            Ticket createdTicket = ticketCommandService.handle(command);

            // Transform entity to resource
            TicketResource ticketResource = TicketResourceFromEntityAssembler.toResourceFromEntity(createdTicket);

            return new ResponseEntity<>(ticketResource, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other errors
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
