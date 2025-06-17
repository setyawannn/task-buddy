package com.taskbuddy.structures.algorithm;

import com.taskbuddy.models.Task;
import java.util.List;
import java.util.ArrayList;


public class TaskSearcher {

    public static List<Task> searchByKeyword(List<Task> tasks, String keyword) {
        List<Task> result = new ArrayList<>();

        if (tasks == null || keyword == null || keyword.trim().isEmpty()) {
            return result; 
        }

        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task == null)
                continue;

            String title = task.getTitle() != null ? task.getTitle().toLowerCase() : "";
            String desc = task.getDescription() != null ? task.getDescription().toLowerCase() : "";

            if (title.contains(lowerKeyword) || desc.contains(lowerKeyword)) {
                result.add(task);
            }
        }

        return result;
    }

    public static void displaySearchResults(List<Task> tasks, String keyword) {
        List<Task> results = searchByKeyword(tasks, keyword);

        System.out.println("\n=== Search Results for: \"" + keyword + "\" ===");
        if (results.isEmpty()) {
            System.out.println("No tasks found with keyword: " + keyword);
            return;
        }

        String[] headers = { "No", "Title", "Description", "Priority", "Status", "Deadline" };
        int[] widths = { 5, 25, 40, 10, 15, 15 };

        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + widths[i] + "s", headers[i]);
        }
        System.out.println();

        int totalWidth = 0;
        for (int w : widths) totalWidth += w;
        System.out.println("-".repeat(totalWidth));

        for (int i = 0; i < results.size(); i++) {
            Task task = results.get(i);

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

        System.out.println("\nTotal found: " + results.size() + " task(s)");
    }

    private static List<String> wrapTextByWord(String text, int width) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > width) {
                lines.add(line.toString());
                line = new StringBuilder();
            }
            if (!line.isEmpty()) {
                line.append(" ");
            }
            line.append(word);
        }

        if (!line.isEmpty()) {
            lines.add(line.toString());
        }

        return lines;
    }
}