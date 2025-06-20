package com.taskbuddy.cli;

import com.taskbuddy.models.Task;
import com.taskbuddy.models.User;
import com.taskbuddy.services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * TaskMenu - Mengelola semua interaksi CRUD untuk Task dan Subtask.
 */
public class TaskMenu {
    private final TaskService taskService;
    private final Scanner scanner;
    private final User currentUser;

    public TaskMenu(TaskService taskService, Scanner scanner, User currentUser) {
        this.taskService = taskService;
        this.scanner = scanner;
        this.currentUser = currentUser;
    }

    /**
     * Metode utama untuk menampilkan menu manajemen tugas.
     */
    public void show() {
        boolean running = true;
        while (running) {
            // Selalu tampilkan pohon tugas terbaru
            taskService.displayTaskTree(currentUser.getId());

            System.out.println("\n--- Task Management Menu ---");
            System.out.println("1. Add New Main Task");
            System.out.println("2. Add Subtask");
            System.out.println("3. Update Task");
            System.out.println("4. Change Task Status");
            System.out.println("5. Delete Task");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        handleCreateTask(null);
                        break;
                    case 2:
                        System.out.print("Enter the ID of the parent task: ");
                        int parentId = Integer.parseInt(scanner.nextLine());
                        handleCreateTask(parentId);
                        break;
                    case 3:
                        handleUpdateTask();
                        break;
                    case 4:
                        handleChangeStatus();
                        break;
                    case 5:
                        handleDeleteTask();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void handleCreateTask(Integer parentId) {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        Task.Priority priority = selectPriority();
        if (priority == null)
            return;

        LocalDate deadline = selectDeadline();

        Task newTask = new Task(title, description, priority, deadline, currentUser.getId());
        if (parentId != null) {
            newTask.setParentTaskId(parentId);
        }

        taskService.createTask(newTask);
    }

    private void handleUpdateTask() {
        System.out.print("Enter the ID of the task to update: ");
        int taskId;
        try {
            taskId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        Task task = taskService.getTaskById(taskId, currentUser.getId());
        if (task == null) {
            System.out.println("Task not found!");
            return;
        }

        System.out.println("Updating task: " + task.getTitle());
        System.out.print("Enter new title (or press Enter to keep '" + task.getTitle() + "'): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            task.setTitle(newTitle);
        }

        System.out.print("Enter new description (or press Enter to keep current): ");
        String newDesc = scanner.nextLine();
        if (!newDesc.isEmpty()) {
            task.setDescription(newDesc);
        }

        System.out.println("Select new priority (or press Enter to keep '" + task.getPriority() + "'): ");
        Task.Priority newPriority = selectPriority();
        if (newPriority != null) {
            task.setPriority(newPriority);
        }

        System.out.println("Enter new deadline (or press Enter to keep current): ");
        LocalDate newDeadline = selectDeadline();
        if (newDeadline != null) {
            task.setDeadline(newDeadline);
        }

        taskService.updateTask(task);
    }

    private void handleChangeStatus() {
        System.out.print("Enter the ID of the task to change status: ");
        int taskId;
        try {
            taskId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        Task task = taskService.getTaskById(taskId, currentUser.getId());
        if (task == null) {
            System.out.println("Task not found!");
            return;
        }

        System.out.println("Current status: " + task.getStatus());
        System.out.println("Select new status:");
        int i = 1;
        for (Task.Status status : Task.Status.values()) {
            System.out.println(i++ + ". " + status);
        }
        System.out.print("Choose option: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= Task.Status.values().length) {
                Task.Status newStatus = Task.Status.values()[choice - 1];
                task.setStatus(newStatus);
                taskService.updateTask(task);
            } else {
                System.out.println("Invalid status choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void handleDeleteTask() {
        System.out.print("Enter the ID of the task to delete: ");
        int taskId;
        try {
            taskId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.println("WARNING: Deleting a task will also delete all its subtasks.");
        System.out.print("Are you sure you want to delete task #" + taskId + "? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            taskService.deleteTask(taskId, currentUser.getId());
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private Task.Priority selectPriority() {
        System.out.println("Select Priority:");
        int i = 1;
        for (Task.Priority p : Task.Priority.values()) {
            System.out.println(i++ + ". " + p);
        }
        System.out.print("Choose option: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= Task.Priority.values().length) {
                return Task.Priority.values()[choice - 1];
            }
        } catch (NumberFormatException e) {
        }
        System.out.println("Invalid priority choice. Action cancelled.");
        return null;
    }

    private LocalDate selectDeadline() {
        System.out.print("Enter Deadline (YYYY-MM-DD) or press Enter for no deadline: ");
        String deadlineStr = scanner.nextLine();
        if (deadlineStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(deadlineStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Deadline not set.");
            return null;
        }
    }
}