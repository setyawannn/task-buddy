package com.taskbuddy.services;

import com.taskbuddy.models.User;
import java.util.LinkedList;
import java.util.Queue;

public class UserQueue {
    private Queue<User> queue;

    public UserQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(User user) {
        queue.offer(user);
        System.out.println("User " + user.getUsername() + " added to the queue.");
    }

    public void dequeue() {
        User user = queue.poll();
        if (user != null) {
            System.out.println("User " + user.getUsername() + " removed from the queue.");
        } else {
            System.out.println("Queue is empty.");
        }
    }

    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Current User Queue:");
        int i = 1;
        for (User user : queue) {
            System.out.println(i++ + ". " + user.getUsername() + " (" + user.getRole() + ")");
        }
    }
}