package ru.kustou.libraryapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        return this.template.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public void create(Book model) {
        String sqlCommand = "INSERT INTO books(name, author, year) VALUES(?, ?, ?)";
        this.template.update(sqlCommand, model.getName(), model.getAuthor(), model.getYear());
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Book model) {

    }
}
