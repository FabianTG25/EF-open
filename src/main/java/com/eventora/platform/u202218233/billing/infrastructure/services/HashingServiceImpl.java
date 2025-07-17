package com.eventora.platform.u202218233.billing.infrastructure.services;

import com.eventora.platform.u202218233.billing.domain.services.HashingService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class HashingServiceImpl implements HashingService {

    private static final String ALGORITHM = "SHA-256";
    private static final String SALT = "EventoraPlatform2025"; // In production, use a more secure salt

    @Override
    public String hashCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

            // Add salt to prevent rainbow table attacks
            String saltedInput = SALT + cardNumber.replaceAll("\\s+", "");

            byte[] hashBytes = digest.digest(saltedInput.getBytes(StandardCharsets.UTF_8));

            // Convert bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Clear sensitive data from memory immediately
            saltedInput = null;

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
