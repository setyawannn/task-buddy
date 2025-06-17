package com.taskbuddy.cli;

import com.taskbuddy.models.User;
import com.taskbuddy.services.UserService;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private Scanner scanner;
    private UserService userService;
    private User currentUser;

    public UserMenu(User currentUser) {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.currentUser = currentUser;
    }

    public void start() {
        if (!currentUser.isAdmin()) {
            System.out.println("Access denied. Admins only.");
            return;
        }

        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    findUserById();
                    break;
                case 3:
                    deleteUserById();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== USER MANAGEMENT MENU ===");
        System.out.println("1. View All Users");
        System.out.println("2. Find User by ID");
        System.out.println("3. Delete User");
        System.out.println("0. Back to Main Menu");
        System.out.print("Choose an option: ");
    }

    private void listAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\n=== USER LIST ===");
        for (User user : users) {
            System.out.println("ID: " + user.getId() +
                    " | Username: " + user.getUsername() +
                    " | Email: " + user.getEmail() +
                    " | Role: " + user.getRole());
        }
    }

    private void findUserById() {
        System.out.print("Enter user ID: ");
        int userId = getChoice();

        User user = userService.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
        } else {
            System.out.println("=== USER FOUND ===");
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Role: " + user.getRole());
        }
    }

    private void deleteUserById() {
        System.out.print("Enter user ID to delete: ");
        int userId = getChoice();

        if (userId == currentUser.getId()) {
            System.out.println("ERROR: You cannot delete your own account.");
            return;
        }

        boolean success = userService.deleteUserById(userId);

        if (success) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found or could not be deleted.");
        }
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

