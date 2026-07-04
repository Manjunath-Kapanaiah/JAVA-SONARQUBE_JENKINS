package com.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Utility class
 */
public class UtilityTest {
    private Utility utility;

    @Before
    public void setUp() {
        utility = new Utility();
    }

    @Test
    public void testGreetWithValidName() {
        String result = utility.greet("Jenkins");
        assertEquals("Hello, Jenkins!", result);
    }

    @Test
    public void testGreetWithEmptyName() {
        String result = utility.greet("");
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testGreetWithNull() {
        String result = utility.greet(null);
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testAdd() {
        int result = utility.add(5, 3);
        assertEquals(8, result);
    }

    @Test
    public void testAddNegativeNumbers() {
        int result = utility.add(-5, -3);
        assertEquals(-8, result);
    }

    @Test
    public void testSubtract() {
        int result = utility.subtract(10, 3);
        assertEquals(7, result);
    }

    @Test
    public void testSubtractNegativeNumbers() {
        int result = utility.subtract(-5, -3);
        assertEquals(-2, result);
    }
}
