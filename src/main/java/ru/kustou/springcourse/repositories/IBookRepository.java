package ru.kustou.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustou.springcourse.models.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
}
