package com.taskbuddy.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
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
            LocalDate deadline, int categoryId, int userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.categoryId = categoryId;
        this.userId = userId;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (Priority: %s, Status: %s)",
                id, title, description, priority, status);
    }

    public int compareTo(Task other) {
        return Integer.compare(this.priority.getValue(), other.priority.getValue());
    }
}