package com.taskbuddy.services;

import com.taskbuddy.models.Task;
import com.taskbuddy.structures.tree.TaskTree;
import com.taskbuddy.utils.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskService - Mengelola operasi Task
 * Menghubungkan Tree structure dengan Database
 */
public class TaskService {
    private TaskTree taskTree;

    public TaskService() {
        this.taskTree = new TaskTree();
    }

    /**
     * Membuat task baru
     */
    public boolean createTask(Task task) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null!");
                return false;
            }

            String sql = "INSERT INTO tasks (title, description, priority, deadline, user_id, parent_task_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().toString());

            // Handle deadline yang bisa null
            if (task.getDeadline() != null) {
                stmt.setDate(4, Date.valueOf(task.getDeadline()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setInt(5, task.getUserId());

            // Handle parent_task_id yang bisa null
            if (task.getParentTaskId() != null) {
                stmt.setInt(6, task.getParentTaskId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, task.getStatus().toString());

            int result = stmt.executeUpdate();

            if (result > 0) {
                // Ambil ID yang baru dibuat
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                }
                rs.close();

                // Tambah ke tree
                taskTree.addTask(task, task.getParentTaskId());

                System.out.println("Task created successfully!");
                stmt.close();
                return true;
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error creating task: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Load semua tasks dari database ke tree untuk user tertentu
     */
    public void loadTasksFromDatabase(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null!");
                return;
            }

            taskTree = new TaskTree();

            String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY parent_task_id, id";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = createTaskFromResultSet(rs);
                if (task != null) {
                    taskTree.addTask(task, task.getParentTaskId());
                }
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper method untuk membuat Task object dari ResultSet
     */
    private Task createTaskFromResultSet(ResultSet rs) throws SQLException {
        try {
            Task task = new Task();

            task.setId(rs.getInt("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));

            // Handle enum Priority
            String priorityStr = rs.getString("priority");
            if (priorityStr != null) {
                task.setPriority(Task.Priority.valueOf(priorityStr));
            } else {
                task.setPriority(Task.Priority.MEDIUM);
            }

            // Handle enum Status
            String statusStr = rs.getString("status");
            if (statusStr != null) {
                task.setStatus(Task.Status.valueOf(statusStr));
            } else {
                task.setStatus(Task.Status.PENDING);
            }

            // Handle deadline yang bisa null
            Date deadlineDate = rs.getDate("deadline");
            if (deadlineDate != null) {
                task.setDeadline(deadlineDate.toLocalDate());
            }

            task.setUserId(rs.getInt("user_id"));

            // Handle category_id yang bisa null
            int categoryId = rs.getInt("category_id");
            if (!rs.wasNull()) {
                task.setCategoryId(categoryId);
            }

            // Handle parent_task_id yang bisa null
            Integer parentTaskId = rs.getObject("parent_task_id", Integer.class);
            task.setParentTaskId(parentTaskId);

            // Handle timestamps
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                task.setCreatedAt(createdAt.toLocalDateTime());
            }

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                task.setUpdatedAt(updatedAt.toLocalDateTime());
            }

            return task;

        } catch (Exception e) {
            System.err.println("Error creating task from ResultSet: " + e.getMessage());
            return null;
        }
    }

    /**
     * Menampilkan tree structure untuk user tertentu
     */
    public void displayTaskTree(int userId) {
        // Clear tree dan reload data terbaru
        taskTree = new TaskTree();
        loadTasksFromDatabase(userId);

        System.out.println("\n=== Your Task Tree ===");
        if (taskTree.getAllTasks().isEmpty()) {
            System.out.println("No tasks found. Create your first task!");
        } else {
            taskTree.displayTree();
        }
    }

    /**
     * \\endapatkan semua tasks untuk user tertentu
     */
    public List<Task> getAllTasks(int userId) {
        loadTasksFromDatabase(userId);
        return taskTree.getAllTasks();
    }

    /**
     * Mendapatkan task berdasarkan ID
     */
    public Task getTaskById(int taskId, int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM tasks WHERE id = ? AND user_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Task task = createTaskFromResultSet(rs);
                rs.close();
                stmt.close();
                return task;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error getting task: " + e.getMessage());
        }

        return null;
    }

    /**
     * Update task
     */
    public boolean updateTask(Task task) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE tasks SET title = ?, description = ?, priority = ?, status = ?, deadline = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriority().toString());
            stmt.setString(4, task.getStatus().toString());

            if (task.getDeadline() != null) {
                stmt.setDate(5, Date.valueOf(task.getDeadline()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setInt(6, task.getId());

            int result = stmt.executeUpdate();
            stmt.close();

            if (result > 0) {
                System.out.println("Task updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error updating task: " + e.getMessage());
        }

        return false;
    }

    /**
     * Delete task
     */
    public boolean deleteTask(int taskId, int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM tasks WHERE id = ? AND user_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);

            int result = stmt.executeUpdate();
            stmt.close();

            if (result > 0) {
                System.out.println("Task deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
        }

        return false;
    }
}