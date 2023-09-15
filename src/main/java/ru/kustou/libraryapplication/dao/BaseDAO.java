package ru.kustou.libraryapplication.dao;

import org.springframework.jdbc.core.JdbcTemplate;


public abstract class BaseDAO<T> implements InterfaceDAO<T> {
    private final JdbcTemplate template;

    public BaseDAO(JdbcTemplate template) {
        this.template = template;
    }
}
