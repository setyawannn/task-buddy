package com.taskbuddy.cli;

import com.taskbuddy.models.User;
import com.taskbuddy.services.UserService;
import com.taskbuddy.utils.InputValidator;
import java.util.Scanner;

public class AuthenticationMenu {
    private Scanner scanner;
    private UserService userService;

    public AuthenticationMenu() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayAuthMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    handleLogin();
                    running = false;
                    break;
                case 2:
                    handleRegister();
                    break;
                case 0:
                    System.out.println("Thank you for visiting TaskBuddy!");
                    running = false;
                    break;
                default:
                    System.out.println("ERROR: Invalid choice! Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void displayAuthMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         TASKBUDDY AUTHENTICATION");
        System.out.println("=".repeat(40));
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.println("=".repeat(40));
        System.out.print("Choose option (0-2): ");
    }

    private void handleLogin() {
        System.out.println("\n=== LOGIN ===");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("ERROR: Username and password cannot be empty!");
            return;
        }

        User user = userService.loginUser(username, password);

        if (user != null) {
            System.out.println("\nRedirecting to main menu...");

            MenuManager menuManager = new MenuManager(user);
            menuManager.start();
        }
    }

    private void handleRegister() {
        System.out.println("\n=== REGISTER ===");

        System.out.print("Username (3-20 characters): ");
        String username = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password (min 6 characters): ");
        String password = scanner.nextLine().trim();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();

        if (!validateRegistrationInput(username, email, password, confirmPassword)) {
            return;
        }

        boolean success = userService.registerUser(username, email, password);

        if (success) {
            System.out.println("You can now login with your new account!");
        }
    }

    private boolean validateRegistrationInput(String username, String email,
            String password, String confirmPassword) {
        if (username.length() < 3 || username.length() > 20) {
            System.out.println("ERROR: Username must be 3-20 characters!");
            return false;
        }

        if (!InputValidator.isValidEmail(email)) {
            System.out.println("ERROR: Invalid email format!");
            return false;
        }

        if (password.length() < 6) {
            System.out.println("ERROR: Password must be at least 6 characters!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("ERROR: Passwords do not match!");
            return false;
        }

        return true;
    }

    private int getChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}