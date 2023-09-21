package ru.kustou.libraryapplication.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kustou.libraryapplication.dao.BookDAO;
import ru.kustou.libraryapplication.models.Book;

import java.time.Year;

@Component
public class BookValidator implements Validator {
    private final BookDAO dao;

    @Autowired
    public BookValidator(BookDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book)target;

        if (this.dao.findByName(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "Name is ready taken");
        }

        if (book.getYear() > Year.now().getValue()) {
            errors.rejectValue("year", "", "Year should not exceed the current one");
        }
    }
}
