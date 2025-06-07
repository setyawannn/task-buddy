package com.taskbuddy.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model Task - template untuk data tugas
 * Implementasi konsep Encapsulation
 */
public class Task {
    // Private fields (Encapsulation)
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate deadline;
    private int categoryId;
    private int userId;
    private Integer parentTaskId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Priority {
        LOW(1), MEDIUM(2), HIGH(3), URGENT(4);

        private final int value;

        Priority(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Task() {
    }

    public Task(String title, String description, Priority priority,
            LocalDate deadline, int userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.userId = userId;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(int id, String title, String description, Priority priority,
            Status status, LocalDate deadline, int categoryId, int userId,
            Integer parentTaskId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.categoryId = categoryId;
        this.userId = userId;
        this.parentTaskId = parentTaskId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        this.updatedAt = LocalDateTime.now();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        this.updatedAt = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Integer parentTaskId) {
        this.parentTaskId = parentTaskId;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        String parentInfo = parentTaskId != null ? " (Subtask of: " + parentTaskId + ")" : "";
        return String.format("[%d] %s - %s (Priority: %s, Status: %s)%s",
                id, title, description, priority, status, parentInfo);
    }

    public int compareTo(Task other) {
        return Integer.compare(this.priority.getValue(), other.priority.getValue());
    }

    public boolean isSubtask() {
        return parentTaskId != null;
    }

    public boolean isMainTask() {
        return parentTaskId == null;
    }
}