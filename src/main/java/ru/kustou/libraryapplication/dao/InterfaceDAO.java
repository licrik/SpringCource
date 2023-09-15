package ru.kustou.libraryapplication.dao;

import java.util.List;

public interface InterfaceDAO<T> {
    T findById(int id);

    List<T> getAll();

    void create(T model);

    void delete(int id);

    void update(T model);
}
