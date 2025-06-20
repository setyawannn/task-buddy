package com.taskbuddy.cli;

import com.taskbuddy.models.Task;
import com.taskbuddy.models.User;
import com.taskbuddy.services.TaskService;
import com.taskbuddy.structures.algorithm.TaskSearcher;
import com.taskbuddy.structures.algorithm.TaskSorter;

import java.util.ArrayList;
import java.util.List;
import com.taskbuddy.structures.linkedlist.ActivityLogger;

import com.taskbuddy.services.UserQueue; // Add this import
import com.taskbuddy.services.UserService; // Add this import
import java.util.Scanner;

/**
 * MenuManager - Menu utama setelah login
 */
public class MenuManager {
    private Scanner scanner;
    private TaskService taskService;
    private ActivityLogger activityLogger;
    private User currentUser;
    private UserQueue userQueue; // Add this field
    private UserService userService; // Add this field
    private TaskMenu taskMenu;

    public MenuManager(User user) {
        this.scanner = new Scanner(System.in);
        this.taskService = new TaskService();
        this.currentUser = user;
        this.userQueue = new UserQueue(); // Initialize userQueue
        this.userService = new UserService(); // Initialize userService
        this.activityLogger = new ActivityLogger();
        this.taskMenu = new TaskMenu(taskService, scanner, currentUser);
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
                    activityLogger.activityLogMenu(scanner);
                    activityLogger.log("User membuka menu Task Tree");
                    break;
                case 3:
                    handleUserQueueMenu();
                    break;
                case 4:
                    handleSearchSortMenu();
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
        activityLogger.log("User membuka menu Task Tree Management");
        taskMenu.show();
    }

    private void handleSearchSortMenu() {
        System.out.println("\n=== Search & Sort Tasks ===");
        System.out.println("1. Search Tasks by Keyword");
        System.out.println("2. Sort Tasks by Priority");
        System.out.println("3. Sort Tasks by Deadline");
        System.out.println("0. Back to Main Menu");
        System.out.print("Choose option: ");

        int choice = getChoice();
        List<Task> userTasks = taskService.getAllTasks(currentUser.getId());

        switch (choice) {
            case 1:
                System.out.print("Enter keyword: ");
                String keyword = scanner.nextLine();
                TaskSearcher.displaySearchResults(userTasks, keyword);
                break;
            case 2:
                TaskSorter.displayTasksSortedByPriority(userTasks);
                break;
            case 3:
                TaskSorter.displayTasksSortedByDeadline(userTasks);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void handleUserQueueMenu() {
        boolean inQueueMenu = true;
        while (inQueueMenu) {
            System.out.println("\n=== User Queue Menu ===");
            System.out.println("1. Tambahkan User ke Antrian");
            System.out.println("2. Proses Antrian (Dequeue)");
            System.out.println("3. Tampilkan Antrian");
            System.out.println("0. Kembali ke Main Menu");
            System.out.print("Pilih menu: ");
            int choice = getChoice();

            switch (choice) {
                case 1:
                    System.out.print("Masukkan username user yang ingin ditambahkan ke antrian: ");
                    String username = scanner.nextLine().trim();
                    User userToAdd = userService.findByUsername(username); 
                    if (userToAdd != null) {
                        userQueue.enqueue(userToAdd);
                    } else {
                        System.out.println("User tidak ditemukan!");
                    }
                    break;
                case 2:
                    userQueue.dequeue();
                    break;
                case 3:
                    userQueue.displayQueue();
                    break;
                case 0:
                    inQueueMenu = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
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