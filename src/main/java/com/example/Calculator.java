package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculator class providing basic arithmetic operations
 */
public class Calculator {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    /**
     * Adds two numbers
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public int add(int a, int b) {
        int result = a + b;
        logger.debug("Adding {} + {} = {}", a, b, result);
        return result;
    }

    /**
     * Subtracts two numbers
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public int subtract(int a, int b) {
        int result = a - b;
        logger.debug("Subtracting {} - {} = {}", a, b, result);
        return result;
    }

    /**
     * Multiplies two numbers
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public int multiply(int a, int b) {
        int result = a * b;
        logger.debug("Multiplying {} * {} = {}", a, b, result);
        return result;
    }

    /**
     * Divides two numbers
     * @param a dividend
     * @param b divisor
     * @return quotient of a and b
     * @throws ArithmeticException if divisor is zero
     */
    public int divide(int a, int b) {
        if (b == 0) {
            logger.error("Division by zero attempted: {} / {}", a, b);
            throw new ArithmeticException("Cannot divide by zero");
        }
        int result = a / b;
        logger.debug("Dividing {} / {} = {}", a, b, result);
        return result;
    }
}