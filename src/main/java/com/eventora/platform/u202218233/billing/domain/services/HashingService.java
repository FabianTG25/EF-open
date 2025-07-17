package com.eventora.platform.u202218233.billing.domain.services;

public interface HashingService {

    /**
     * Generates a secure hash from a credit card number.
     * The original card number is not stored or retained in memory.
     * 
     * @param cardNumber The credit card number to hash
     * @return A secure hash representation of the card number
     * @throws IllegalArgumentException if cardNumber is null or empty
     */
    String hashCardNumber(String cardNumber);
}
