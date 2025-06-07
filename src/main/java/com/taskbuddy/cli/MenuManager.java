package com.taskbuddy.cli;

import java.util.Scanner;

public class MenuManager {
    private Scanner scanner;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    System.out.println("Task Tree Management - Coming Soon!");
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
                    System.out.println("User Management - Coming Soon!");
                    break;
                case 0:
                    System.out.println("Thank you for using TaskBuddy!");
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

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           TASKBUDDY MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Task Tree Management    (Adi)");
        System.out.println("2. Activity Log           (Zaidan)");
        System.out.println("3. User Queue             (Dhani)");
        System.out.println("4. Search & Sort Tasks    (Rafi)");
        System.out.println("5. User Management        (Rafael)");
        System.out.println("0. Exit");
        System.out.println("=".repeat(40));
        System.out.print("Choose option (0-5): ");
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