package com.taskbuddy.structures.linkedlist;

import com.taskbuddy.structures.linkedlist.LogNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * ActivityLogger - Mencatat dan menampilkan log aktivitas menggunakan double linked list
 */
public class ActivityLogger {
    private LogNode head;
    private LogNode tail;

    public static class ActivityLog {
        private String message;
        private LocalDateTime timestamp;

        public ActivityLog(String message) {
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return "[" + timestamp.format(formatter) + "] " + message;
        }
    }

    public void log(String message) {
        ActivityLog newLog = new ActivityLog(message);
        LogNode newNode = new LogNode(newLog);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
    }

    public void displayLogsForward() {
        if (head == null) {
            System.out.println("Belum ada log aktivitas.");
            return;
        }

        LogNode current = head;
        System.out.println("\n=== Log Aktivitas (Awal ke Akhir) ===");
        while (current != null) {
            System.out.println(current.getLog().toString());
            current = current.getNext();
        }
    }

    public void displayLogsBackward() {
        if (tail == null) {
            System.out.println("Belum ada log aktivitas.");
            return;
        }

        LogNode current = tail;
        System.out.println("\n=== Log Aktivitas (Akhir ke Awal) ===");
        while (current != null) {
            System.out.println(current.getLog().toString());
            current = current.getPrevious();
        }
    }

    public void searchLog(String keyword) {
        if (head == null) {
            System.out.println("Belum ada log aktivitas.");
            return;
        }

        boolean found = false;
        LogNode current = head;
        System.out.println("\n=== Hasil Pencarian Log ===");

        while (current != null) {
            if (current.getLog().getMessage().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(current.getLog().toString());
                found = true;
            }
            current = current.getNext();
        }

        if (!found) {
            System.out.println("Tidak ditemukan log yang mengandung: \"" + keyword + "\"");
        }
    }

    public void activityLogMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("\n=== MENU ACTIVITY LOG ===");
            System.out.println("1. Tampilkan semua log (urutan awal ke akhir)");
            System.out.println("2. Tampilkan semua log (urutan akhir ke awal)");
            System.out.println("3. Cari log berdasarkan keyword");
            System.out.println("4. Kembali ke menu utama");
            System.out.print("Pilih menu: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    displayLogsForward();
                    break;
                case 2:
                    displayLogsBackward();
                    break;
                case 3:
                    System.out.print("Masukkan keyword: ");
                    String keyword = scanner.nextLine();
                    searchLog(keyword);
                    break;
                case 4:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }

        } while (choice != 4);
    }
}
