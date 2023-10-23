package ru.kustou.springcourse.services.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kustou.springcourse.models.Book;
import ru.kustou.springcourse.models.Person;
import ru.kustou.springcourse.repositories.IBookRepository;
import ru.kustou.springcourse.services.IBookService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService implements IBookService {
    private final IBookRepository repository;

    @Autowired
    public BookService(IBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAll(boolean isSort) {
        if (isSort) {
            return this.repository.findAll(Sort.by("year"));
        }

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

        if (person != null) {
            optionalBook.ifPresent(book -> {
                book.setOwner(person);
                book.setTimeTaken(new Date());
            });
        }
        else {
            optionalBook.ifPresent(book -> {
                book.setOwner(null);
                book.setTimeTaken(null);
            });
        }
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return this.repository.findByNameContaining(keyword)
                .stream().peek(book -> {Hibernate.initialize(book.getOwner());})
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAll(Integer page, Integer size, boolean haveSort) {
        if (page != null || size != null) {
           return this.getWithPage(page, size, haveSort);
        }

        return this.getWithoutPage(haveSort);
    }

    private List<Book> getWithPage(Integer page, Integer size, boolean haveSort) {
        if (haveSort) {
            return this.repository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
        }

        return this.repository.findAll(PageRequest.of(page, size)).getContent();
    }

    private List<Book> getWithoutPage(boolean haveSort) {
        if (haveSort) {
            return this.repository.findAll(Sort.by("year"));
        }

        return this.repository.findAll();
    }
}
