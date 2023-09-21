package ru.kustou.libraryapplication.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kustou.libraryapplication.dao.PersonDAO;
import ru.kustou.libraryapplication.models.Person;

import java.time.Year;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO dao;

    @Autowired
    public PersonValidator(PersonDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;

        if (this.dao.findByFio(person.getFio()).isPresent()) {
           errors.rejectValue("fio", "", "FIO is ready taken");
        }

        if (person.getYear() > Year.now().getValue()) {
            errors.rejectValue("year", "", "Year should not exceed the current one");
        }
    }
}
