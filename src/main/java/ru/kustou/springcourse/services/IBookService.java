package ru.kustou.springcourse.services;

import org.springframework.stereotype.Component;
import ru.kustou.springcourse.models.Book;
import ru.kustou.springcourse.models.Person;

import java.util.List;

@Component
public interface IBookService {
    List<Book> getAll(boolean isSort);
    List<Book> getAll(Integer page, Integer count, boolean isSort);
    Book getById(int id);
    void create(Book book);
    void update(int id, Book book);
    void delete(int id);
    Book findWithOwner(int id);
    void updateOwner(int id, Person person);
    List<Book> searchBook(String keyword);

}
