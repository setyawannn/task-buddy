package com.taskbuddy.interfaces;

public interface Manageable<T> {
    void create(T item);

    T read(int id);

    void update(T item);

    void delete(int id);

    java.util.List<T> getAll();
}