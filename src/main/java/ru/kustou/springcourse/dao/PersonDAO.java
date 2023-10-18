package ru.kustou.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kustou.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO extends BaseDAO<Person> {

    @Override
    public Optional<Person> findById(int id) {
        String command = "SELECT * FROM person WHERE id=?";
        return this.template.query(command, new Object[] {id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    @Override
    public List<Person> getAll() {
        String command = "SELECT * FROM person";

        return this.template.query(command, new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public void create(Person model) {
        String command = "INSERT INTO person(fio, year) VALUES(?, ?)";
        this.template.update(command, model.getFio(), model.getYear());
    }

    @Override
    public void delete(int id) {
        String command = "DELETE FROM person WHERE id=?";
        this.template.update(command, id);
    }

    @Override
    public void update(int id, Person model) {
        String command = "UPDATE person SET fio=?, year=? WHERE id=?";
        this.template.update(command, model.getFio(), model.getYear(), id);
    }

    public Optional<Person> findByFio(String fio) {
        String command = "SELECT * FROM people WHERE fio=?";

        return this.template.query(command, new Object[] {fio}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}