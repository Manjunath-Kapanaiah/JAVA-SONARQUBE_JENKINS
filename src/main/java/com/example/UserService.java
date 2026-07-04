package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UserService class for user-related operations
 */
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Displays a welcome message for the user
     * @param userName name of the user
     */
    public void displayWelcome(String userName) {
        if (userName == null || userName.isEmpty()) {
            logger.warn("User name is empty");
            return;
        }
        String welcomeMessage = "Welcome, " + userName + "!";
        logger.info(welcomeMessage);
        System.out.println(welcomeMessage);
    }

    /**
     * Validates user age
     * @param age age of the user
     * @return true if age is valid (>= 18), false otherwise
     */
    public boolean isAdult(int age) {
        boolean isValid = age >= 18;
        logger.debug("Age validation for age {}: {}", age, isValid);
        return isValid;
    }
}