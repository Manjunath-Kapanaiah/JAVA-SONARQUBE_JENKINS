package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class with common helper methods
 */
public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    /**
     * Greet a user with a message
     * @param name the name to greet
     * @return greeting message
     */
    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("Name is empty or null");
            return "Hello, World!";
        }
        logger.info("Greeting {}", name);
        return "Hello, " + name + "!";
    }

    /**
     * Add two numbers
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public int add(int a, int b) {
        logger.debug("Adding {} + {}", a, b);
        return a + b;
    }

    /**
     * Subtract two numbers
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public int subtract(int a, int b) {
        logger.debug("Subtracting {} - {}", a, b);
        return a - b;
    }
}
