package com.taskbuddy.interfaces;

public interface Searchable<T> {
    T search(T[] items, String keyword);

    java.util.List<T> searchAll(java.util.List<T> items, String keyword);
}
