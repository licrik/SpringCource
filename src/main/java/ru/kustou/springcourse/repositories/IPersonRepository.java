package ru.kustou.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustou.springcourse.models.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {
}
