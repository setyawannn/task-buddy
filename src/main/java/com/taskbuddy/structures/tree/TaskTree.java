package com.taskbuddy.structures.tree;

import com.taskbuddy.models.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree structure untuk mengelola hierarki tasks
 * Implementasi N-ary tree untuk kategori dan subtasks
 */
public class TaskTree {
    private TreeNode root;

    public TaskTree() {
        // Root node dengan dummy task
        this.root = new TreeNode(null);
    }

    /**
     * Menambah task baru ke tree
     * 
     * @param task         Task yang akan ditambahkan
     * @param parentTaskId ID parent task (null jika root level)
     */
    public void addTask(Task task, Integer parentTaskId) {
        TreeNode newNode = new TreeNode(task);

        if (parentTaskId == null) {
            // Add as root level task
            root.addChild(newNode);
        } else {
            // Find parent node and add as child
            TreeNode parentNode = findNode(root, parentTaskId);
            if (parentNode != null) {
                parentNode.addChild(newNode);
            } else {
                // If parent not found, add as root level
                root.addChild(newNode);
            }
        }
    }

    /**
     * Mencari node berdasarkan task ID
     */
    private TreeNode findNode(TreeNode node, int taskId) {
        if (node.getTask() != null && node.getTask().getId() == taskId) {
            return node;
        }

        for (TreeNode child : node.getChildren()) {
            TreeNode found = findNode(child, taskId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Display tree structure (Pre-order traversal)
     */
    public void displayTree() {
        System.out.println("=== Task Tree Structure ===");
        displayNode(root, 0);
    }

    private void displayNode(TreeNode node, int level) {
        if (node.getTask() != null) {
            String indent = "  ".repeat(level);
            System.out.println(indent + "├─ " + node.getTask().toString());
        }

        for (TreeNode child : node.getChildren()) {
            displayNode(child, level + 1);
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        collectTasks(root, tasks);
        return tasks;
    }

    private void collectTasks(TreeNode node, List<Task> tasks) {
        if (node.getTask() != null) {
            tasks.add(node.getTask());
        }

        for (TreeNode child : node.getChildren()) {
            collectTasks(child, tasks);
        }
    }
}