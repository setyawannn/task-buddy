package com.taskbuddy.cli;

import com.taskbuddy.models.User;
import com.taskbuddy.services.TaskService;
import java.util.Scanner;

/**
 * MenuManager - Menu utama setelah login
 */
public class MenuManager {
    private Scanner scanner;
    private TaskService taskService;
    private User currentUser;

    public MenuManager(User user) {
        this.scanner = new Scanner(System.in);
        this.taskService = new TaskService();
        this.currentUser = user;
    }

    public void start() {
        System.out.println("\nWelcome, " + currentUser.getUsername() + "!");
        if (currentUser.isAdmin()) {
            System.out.println("You are logged in as ADMIN");
        }

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    handleTaskTreeMenu();
                    break;
                case 2:
                    System.out.println("Activity Log - Coming Soon!");
                    break;
                case 3:
                    System.out.println("User Queue - Coming Soon!");
                    break;
                case 4:
                    System.out.println("Search & Sort Tasks - Coming Soon!");
                    break;
                case 5:
                    if (currentUser.isAdmin()) {
                        System.out.println("User Management - Coming Soon!");
                    } else {
                        System.out.println("Access denied! Admin only feature.");
                    }
                    break;
                case 9:
                    displayUserProfile();
                    break;
                case 0:
                    System.out.println("Logging out... Thank you, " + currentUser.getUsername() + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void handleTaskTreeMenu() {
        System.out.println("\n=== Task Tree Management ===");
        System.out.println("Logged in as: " + currentUser.getUsername());
        System.out.println("1. View My Task Tree");
        System.out.println("2. Add New Task");
        System.out.println("3. Add Subtask");
        System.out.println("0. Back to Main Menu");
        System.out.print("Choose option: ");

        int choice = getChoice();
        switch (choice) {
            case 1:
                // PERBAIKAN: Pass user ID ke method
                taskService.displayTaskTree(currentUser.getId());
                break;
            case 2:
                System.out.println("Add New Task - Coming Soon!");
                break;
            case 3:
                System.out.println("Add Subtask - Coming Soon!");
                break;
        }
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           TASKBUDDY MAIN MENU");
        System.out.println("         User: " + currentUser.getUsername() +
                " (" + currentUser.getRole() + ")");
        System.out.println("=".repeat(50));
        System.out.println("1. Task Tree Management    (Adi)");
        System.out.println("2. Activity Log           (Zaidan)");
        System.out.println("3. User Queue             (Dhani)");
        System.out.println("4. Search & Sort Tasks    (Rafi)");

        if (currentUser.isAdmin()) {
            System.out.println("5. User Management        (Rafael) [ADMIN]");
        } else {
            System.out.println("5. User Management        [ADMIN ONLY]");
        }

        System.out.println("9. My Profile");
        System.out.println("0. Logout");
        System.out.println("=".repeat(50));
        System.out.print("Choose option: ");
    }

    private void displayUserProfile() {
        System.out.println("\n=== USER PROFILE ===");
        System.out.println("ID: " + currentUser.getId());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Role: " + currentUser.getRole());
        if (currentUser.getCreatedAt() != null) {
            System.out.println("Account created: " + currentUser.getCreatedAt());
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