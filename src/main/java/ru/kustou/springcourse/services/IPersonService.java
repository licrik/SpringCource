package ru.kustou.springcourse.services;

import org.springframework.stereotype.Component;
import ru.kustou.springcourse.models.Person;

import java.util.List;

@Component
public interface IPersonService {
    Person getById(int id);
    Person getByIdWithBooks(int id);
    List<Person> getAll();
    void create(Person person);
    void update(int id, Person person);
    void delete(int id);
}
