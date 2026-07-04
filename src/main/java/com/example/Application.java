package com.example;

/**
 * Main Application class
 */
public class Application {

    /**
     * Main method to start the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Java Application with Jenkins Pipeline");
        System.out.println("========================================");
        
        try {
            Utility utility = new Utility();
            System.out.println("Application initialized successfully");
            
            // Example usage
            String result = utility.greet("Jenkins Pipeline");
            System.out.println("Result: " + result);
            
            System.out.println("========================================");
            System.out.println("Application completed successfully");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("Error running application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
