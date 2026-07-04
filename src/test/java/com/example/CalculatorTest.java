package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Calculator class
 */
public class CalculatorTest {
    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        int result = calculator.add(10, 20);
        assertEquals(30, result);
    }

    @Test
    public void testAddNegativeNumbers() {
        int result = calculator.add(-10, -20);
        assertEquals(-30, result);
    }

    @Test
    public void testSubtract() {
        int result = calculator.subtract(30, 10);
        assertEquals(20, result);
    }

    @Test
    public void testSubtractNegativeResult() {
        int result = calculator.subtract(10, 30);
        assertEquals(-20, result);
    }

    @Test
    public void testMultiply() {
        int result = calculator.multiply(5, 6);
        assertEquals(30, result);
    }

    @Test
    public void testMultiplyByZero() {
        int result = calculator.multiply(5, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDivide() {
        int result = calculator.divide(100, 5);
        assertEquals(20, result);
    }

    @Test
    public void testDivideNegativeNumbers() {
        int result = calculator.divide(-100, 5);
        assertEquals(-20, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        calculator.divide(100, 0);
    }
}