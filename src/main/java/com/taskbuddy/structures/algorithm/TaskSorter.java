package com.taskbuddy.structures.algorithm;

import com.taskbuddy.models.Task;
import java.util.List;
import java.util.ArrayList;

public class TaskSorter {

    public static List<Task> sortByPriority(List<Task> tasks) {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        for (int i = 0; i < sortedTasks.size() - 1; i++) {
            for (int j = 0; j < sortedTasks.size() - i - 1; j++) {
                if (priorityToInt(sortedTasks.get(j).getPriority()) < priorityToInt(
                        sortedTasks.get(j + 1).getPriority())) {
                    Task temp = sortedTasks.get(j);
                    sortedTasks.set(j, sortedTasks.get(j + 1));
                    sortedTasks.set(j + 1, temp);
                }
            }
        }
        return sortedTasks;
    }

    private static int priorityToInt(Task.Priority priority) {
        return switch (priority) {
            case URGENT -> 4;
            case HIGH -> 3;
            case MEDIUM -> 2;
            case LOW -> 1;
            default -> 0;
        };
    }

    public static List<Task> sortByDeadline(List<Task> tasks) {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        List<Task> tasksWithDeadline = new ArrayList<>();
        List<Task> tasksWithoutDeadline = new ArrayList<>();

        for (Task task : sortedTasks) {
            if (task.getDeadline() != null) {
                tasksWithDeadline.add(task);
            } else {
                tasksWithoutDeadline.add(task);
            }
        }

        for (int i = 0; i < tasksWithDeadline.size() - 1; i++) {
            for (int j = 0; j < tasksWithDeadline.size() - i - 1; j++) {
                if (tasksWithDeadline.get(j).getDeadline().isAfter(tasksWithDeadline.get(j + 1).getDeadline())) {
                    Task temp = tasksWithDeadline.get(j);
                    tasksWithDeadline.set(j, tasksWithDeadline.get(j + 1));
                    tasksWithDeadline.set(j + 1, temp);
                }
            }
        }

        List<Task> result = new ArrayList<>();
        result.addAll(tasksWithDeadline);
        result.addAll(tasksWithoutDeadline);
        return result;
    }

    public static void displayTasksSortedByPriority(List<Task> tasks) {
        List<Task> sortedTasks = sortByPriority(tasks);
        System.out.println("\n=== Tasks Sorted by Priority ===");
        displayTaskTable(sortedTasks);
    }

    public static void displayTasksSortedByDeadline(List<Task> tasks) {
        List<Task> sortedTasks = sortByDeadline(tasks);
        System.out.println("\n=== Tasks Sorted by Deadline ===");
        displayTaskTable(sortedTasks);
    }

    private static void displayTaskTable(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        String[] headers = { "No", "Title", "Description", "Priority", "Status", "Deadline" };
        int[] widths = { 5, 25, 40, 10, 15, 15 }; 

        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + widths[i] + "s", headers[i]);
        }
        System.out.println();

        int totalWidth = 0;
        for (int w : widths)
            totalWidth += w;
        System.out.println("-".repeat(totalWidth));

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            String no = String.valueOf(i + 1);
            String title = task.getTitle() != null ? task.getTitle() : "";
            String description = task.getDescription() != null ? task.getDescription() : "No description";
            String deadline = task.getDeadline() != null ? task.getDeadline().toString() : "No deadline";

            List<String> titleLines = wrapTextByWord(title, widths[1]);
            List<String> descLines = wrapTextByWord(description, widths[2]);

            int maxLines = Math.max(titleLines.size(), descLines.size());

            for (int j = 0; j < maxLines; j++) {
                System.out.printf("%-" + widths[0] + "s", (j == 0 ? no : ""));
                System.out.printf("%-" + widths[1] + "s", (j < titleLines.size() ? titleLines.get(j) : ""));
                System.out.printf("%-" + widths[2] + "s", (j < descLines.size() ? descLines.get(j) : ""));
                System.out.printf("%-" + widths[3] + "s", (j == 0 ? task.getPriority() : ""));
                System.out.printf("%-" + widths[4] + "s", (j == 0 ? task.getStatus() : ""));
                System.out.printf("%-" + widths[5] + "s", (j == 0 ? deadline : ""));
                System.out.println();
            }
        }
    }

    private static List<String> wrapTextByWord(String text, int width) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > width) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (currentLine.length() > 0)
                    currentLine.append(" ");
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}