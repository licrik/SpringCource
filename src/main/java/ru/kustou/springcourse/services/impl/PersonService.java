package ru.kustou.springcourse.services.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kustou.springcourse.models.Person;
import ru.kustou.springcourse.repositories.IPersonRepository;
import ru.kustou.springcourse.services.IPersonService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService implements IPersonService {
    private final IPersonRepository repository;

    @Autowired
    public PersonService(IPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person getById(int id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Person getByIdWithBooks(int id) {
        Optional<Person> optionalPerson = this.repository.findById(id);

        optionalPerson.ifPresent(person -> Hibernate.initialize(person.getBooks()));

        return optionalPerson.orElse(null);
    }

    @Override
    public List<Person> getAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public void create(Person person) {
        this.repository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        this.repository.save(person);
    }

    @Override
    @Transactional
    public void delete(int id) {
        this.repository.deleteById(id);
    }
}
