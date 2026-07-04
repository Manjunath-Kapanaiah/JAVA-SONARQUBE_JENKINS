package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Application class for the Java SonarQube Jenkins project
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Application started");
        
        Calculator calculator = new Calculator();
        int sum = calculator.add(10, 20);
        int difference = calculator.subtract(30, 10);
        int product = calculator.multiply(5, 6);
        int quotient = calculator.divide(100, 5);

        logger.info("Sum of 10 and 20: {}", sum);
        logger.info("Difference of 30 and 10: {}", difference);
        logger.info("Product of 5 and 6: {}", product);
        logger.info("Quotient of 100 and 5: {}", quotient);

        UserService userService = new UserService();
        userService.displayWelcome("Developer");

        logger.info("Application completed successfully");
    }
}