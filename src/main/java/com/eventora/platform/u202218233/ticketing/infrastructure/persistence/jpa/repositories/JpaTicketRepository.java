package com.eventora.platform.u202218233.ticketing.infrastructure.persistence.jpa.repositories;

import com.eventora.platform.u202218233.ticketing.domain.model.aggregates.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository interface for Ticket entity.
 * Provides concrete persistence operations for tickets using Spring Data JPA.
 * This interface will be automatically implemented by Spring Data JPA.
 * 
 * @author Fabian Reyes Trujillano - u202218233
 */
@Repository
public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Finds all tickets for a specific event.
     *
     * @param eventId The event UUID
     * @return List of tickets for the event
     */
    @Query("SELECT t FROM Ticket t WHERE t.eventId = :eventId")
    List<Ticket> findByEventId(@Param("eventId") UUID eventId);

    /**
     * Finds tickets by buyer email.
     *
     * @param buyerEmail The buyer email address
     * @return List of tickets for the buyer
     */
    @Query("SELECT t FROM Ticket t WHERE t.buyerEmail.address = :buyerEmail")
    List<Ticket> findByBuyerEmail(@Param("buyerEmail") String buyerEmail);

    /**
     * Finds tickets by buyer name.
     *
     * @param buyerName The buyer name
     * @return List of tickets for the buyer
     */
    List<Ticket> findByBuyerName(String buyerName);

    /**
     * Checks if a ticket exists for a given event and buyer email.
     *
     * @param eventId The event UUID
     * @param buyerEmail The buyer email
     * @return true if ticket exists, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM Ticket t WHERE t.eventId = :eventId AND t.buyerEmail.address = :buyerEmail")
    boolean existsByEventIdAndBuyerEmail(@Param("eventId") UUID eventId, @Param("buyerEmail") String buyerEmail);

    /**
     * Counts the total number of tickets sold for an event.
     *
     * @param eventId The event UUID
     * @return Number of tickets sold
     */
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.eventId = :eventId")
    Long countTicketsByEventId(@Param("eventId") UUID eventId);

    /**
     * Finds all tickets with prices within a range.
     *
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of tickets within price range
     */
    @Query("SELECT t FROM Ticket t WHERE t.price BETWEEN :minPrice AND :maxPrice")
    List<Ticket> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Additional JPA-specific methods for infrastructure layer

    /**
     * Finds tickets ordered by purchase date descending.
     *
     * @return List of tickets ordered by purchase date
     */
    @Query("SELECT t FROM Ticket t ORDER BY t.purchasedAt DESC")
    List<Ticket> findAllOrderByPurchasedAtDesc();

    /**
     * Finds tickets by buyer name containing a substring (case insensitive).
     *
     * @param buyerNamePart Part of the buyer name to search
     * @return List of tickets matching the criteria
     */
    @Query("SELECT t FROM Ticket t WHERE LOWER(t.buyerName) LIKE LOWER(CONCAT('%', :buyerNamePart, '%'))")
    List<Ticket> findByBuyerNameContainingIgnoreCase(@Param("buyerNamePart") String buyerNamePart);

    /**
     * Finds tickets purchased within a date range.
     *
     * @param startDate Start date
     * @param endDate End date
     * @return List of tickets purchased within the range
     */
    @Query("SELECT t FROM Ticket t WHERE t.purchasedAt BETWEEN :startDate AND :endDate")
    List<Ticket> findByPurchasedAtBetween(@Param("startDate") java.util.Date startDate, @Param("endDate") java.util.Date endDate);

    /**
     * Finds tickets with price greater than specified amount.
     *
     * @param price Minimum price
     * @return List of tickets with price greater than the specified amount
     */
    List<Ticket> findByPriceGreaterThan(Double price);

    /**
     * Finds tickets with price less than specified amount.
     *
     * @param price Maximum price
     * @return List of tickets with price less than the specified amount
     */
    List<Ticket> findByPriceLessThan(Double price);
}
