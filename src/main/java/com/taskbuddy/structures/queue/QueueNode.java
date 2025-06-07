package com.taskbuddy.structures.queue;

import com.taskbuddy.models.User;

public class QueueNode {
    private User user;
    private QueueNode next;

    public QueueNode(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public QueueNode getNext() {
        return next;
    }

    public void setNext(QueueNode next) {
        this.next = next;
    }
}