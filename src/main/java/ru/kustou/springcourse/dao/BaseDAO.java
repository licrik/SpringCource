package ru.kustou.springcourse.dao;


import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {
    protected final JdbcTemplate template;

    public BaseDAO() {
        this.template = null;
    }
}