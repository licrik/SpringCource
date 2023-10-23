package ru.kustou.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustou.springcourse.models.Book;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContaining(String name);
}
