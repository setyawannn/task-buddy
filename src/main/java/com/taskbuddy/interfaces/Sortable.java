package com.taskbuddy.interfaces;

public interface Sortable<T> {
    void sort(T[] items);

    void sort(java.util.List<T> items);
}