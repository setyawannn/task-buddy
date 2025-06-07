package com.taskbuddy;

import com.taskbuddy.cli.AuthenticationMenu;
import com.taskbuddy.cli.MenuManager;
import com.taskbuddy.utils.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.initialize();

            if (DatabaseConnection.testConnection()) {
                System.out.println("All good now.\n");

                AuthenticationMenu authMenu = new AuthenticationMenu();
                authMenu.start();
            } else {
                System.err.println("Cannot start application - Database connection failed!");
            }

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
        } finally {
            DatabaseConnection.close();
        }
    }
}