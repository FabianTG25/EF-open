package com.eventora.platform.u202218233.billing.application.internal.outboundServices.acl;

import java.util.Optional;


public interface TicketingContextFacade {
    boolean existsTicketById(Long ticketId);
    Optional<Double> getTicketPrice(Long ticketId);
}
