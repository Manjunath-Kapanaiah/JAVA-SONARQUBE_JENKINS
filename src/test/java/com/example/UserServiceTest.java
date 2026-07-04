package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for UserService class
 */
public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testIsAdultWithValidAge() {
        assertTrue(userService.isAdult(25));
    }

    @Test
    public void testIsAdultWithMinimumAge() {
        assertTrue(userService.isAdult(18));
    }

    @Test
    public void testIsAdultWithMinorAge() {
        assertFalse(userService.isAdult(15));
    }

    @Test
    public void testIsAdultWithNegativeAge() {
        assertFalse(userService.isAdult(-5));
    }

    @Test
    public void testDisplayWelcomeWithValidName() {
        // This test verifies that no exception is thrown
        userService.displayWelcome("John");
    }

    @Test
    public void testDisplayWelcomeWithEmptyName() {
        // This test verifies that no exception is thrown
        userService.displayWelcome("");
    }

    @Test
    public void testDisplayWelcomeWithNullName() {
        // This test verifies that no exception is thrown
        userService.displayWelcome(null);
    }
}