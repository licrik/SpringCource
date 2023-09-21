package ru.kustou.libraryapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kustou.libraryapplication.models.Book;
import ru.kustou.libraryapplication.models.BookWithFio;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO extends BaseDAO<Book> {
    @Autowired
    public BookDAO(JdbcTemplate template) {
        super(template);
    }

    @Override
    public Optional<Book> findById(int id) {
        String command = "SELECT * FROM books WHERE books.id=?";
        return this.template.query(command, new Object[] {id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }

    public Optional<Book> findByName(String name) {
        String command = "SELECT * FROM books WHERE books.name=?";
        return this.template.query(command, new Object[] {name}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
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
        String command = "DELETE FROM books WHERE id=?";
        this.template.update(command, id);
    }

    @Override
    public void update(int id, Book model) {
        String command = "UPDATE books SET name=?, author=?, year=? WHERE id=?";
        this.template.update(command,
                model.getName(),
                model.getAuthor(),
                model.getYear(),
                id);
    }

    public List<Book> findByClientId(int id) {
        String command = "SELECT * FROM books WHERE clientId=?";

        return this.template.query(command, new Object[] {id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void updateClientById(int bookId, int clientId) {
        String command = "UPDATE books SET clientId=? WHERE id=?";
        this.template.update(command, clientId, bookId);
    }

    public Optional<BookWithFio> findWithClientById(int id) {
        String command = "SELECT books.Id, books.name, books.author, books.year, person.fio FROM books LEFT JOIN person ON books.clientId=person.id WHERE books.id=?";
        return this.template.query(command, new Object[] {id}, new BeanPropertyRowMapper<>(BookWithFio.class))
                .stream().findAny();
    }

    public void freeBookBy(int bookId) {
        String command = "UPDATE books SET clientId=null WHERE id=?";
        this.template.update(command, bookId);
    }
}
