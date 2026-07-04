package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Application class
 */
public class ApplicationTest {

    @Test
    public void testApplicationCanRun() {
        // Test that application main method can be called without throwing exception
        try {
            Application.main(new String[]{});
        } catch (Exception e) {
            fail("Application main method threw exception: " + e.getMessage());
        }
    }
}
