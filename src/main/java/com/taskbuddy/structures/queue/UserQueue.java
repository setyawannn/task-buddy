package com.taskbuddy.structures.queue;

import com.taskbuddy.models.User;

public class UserQueue {
    private QueueNode front, rear;

    public UserQueue() {
        front = rear = null;
    }

    // Menambahkan user ke antrian (enqueue)
    public void enqueue(User user) {
        QueueNode newNode = new QueueNode(user);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        System.out.println(user.getUsername() + " masuk ke antrian.");
    }

    // Mengeluarkan user dari antrian (dequeue)
    public User dequeue() {
        if (front == null) {
            System.out.println("Antrian kosong!");
            return null;
        }

        User removedUser = front.getUser();
        front = front.getNext();

        if (front == null) {
            rear = null;
        }

        System.out.println(removedUser.getUsername() + " keluar dari antrian.");
        return removedUser;
    }

    // Menampilkan semua isi antrian
    public void displayQueue() {
        if (front == null) {
            System.out.println("Antrian kosong.");
            return;
        }

        System.out.println("Isi Antrian:");
        QueueNode current = front;
        int index = 1;

        while (current != null) {
            User user = current.getUser();
            System.out.println(index + ". " + user); // Panggil toString()
            current = current.getNext();
            index++;
        }
    }

    // Mengecek apakah antrian kosong
    public boolean isEmpty() {
        return front == null;
    }

    // Mengintip user terdepan
    public User peek() {
        return front != null ? front.getUser() : null;
    }
}
