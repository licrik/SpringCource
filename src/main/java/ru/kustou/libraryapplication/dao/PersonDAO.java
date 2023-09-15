package ru.kustou.libraryapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kustou.libraryapplication.models.Person;

import java.util.List;

@Component
public class PersonDAO extends BaseDAO<Person> {
    @Autowired
    public PersonDAO(JdbcTemplate template) {
        super(template);
    }

    @Override
    public Person findById(int id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public void create(Person model) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Person model) {

    }
}
