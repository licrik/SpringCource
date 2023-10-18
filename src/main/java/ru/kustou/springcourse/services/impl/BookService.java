package ru.kustou.springcourse.services.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kustou.springcourse.models.Book;
import ru.kustou.springcourse.models.Person;
import ru.kustou.springcourse.repositories.IBookRepository;
import ru.kustou.springcourse.services.IBookService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService implements IBookService {
    private final IBookRepository repository;

    @Autowired
    public BookService(IBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Book getById(int id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void create(Book book) {
        this.repository.save(book);
    }

    @Override
    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        this.repository.save(book);
    }

    @Override
    @Transactional
    public void delete(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public Book findWithOwner(int id) {
        Book book = this.repository.findById(id).orElse(null);

        if (book != null && book.getOwner() != null) {
            Hibernate.initialize(book.getOwner());
        }

        return book;
    }

    @Override
    @Transactional
    public void updateOwner(int id, Person person) {
        Optional<Book> optionalBook = this.repository.findById(id);

        optionalBook.ifPresent(book -> book.setOwner(person));
    }
}
