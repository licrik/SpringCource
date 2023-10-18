package ru.kustou.springcourse.dao;

import java.util.List;
import java.util.Optional;

public interface InterfaceDAO<T> {
    Optional<T> findById(int id);

    List<T> getAll();

    void create(T model);

    void delete(int id);

    void update(int id, T model);
}