package ru.kustou.libraryapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kustou.libraryapplication.models.Book;

import java.util.List;

@Component
public class BookDAO extends BaseDAO<Book> {
    @Autowired
    public BookDAO(JdbcTemplate template) {
        super(template);
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void create(Book model) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Book model) {

    }
}
