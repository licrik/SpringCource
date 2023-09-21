package ru.kustou.libraryapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kustou.libraryapplication.dao.BookDAO;
import ru.kustou.libraryapplication.dao.PersonDAO;
import ru.kustou.libraryapplication.models.Book;
import ru.kustou.libraryapplication.models.Person;
import ru.kustou.libraryapplication.utils.PersonValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator validator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator validator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.validator = validator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.getAll());

        return "people/index";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (this.haveError(person, bindingResult)) {
            return "people/new";
        }

        this.personDAO.create(person);

        return "redirect:/people";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person")Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable int id, Model model) {
        Optional<Person> person = this.personDAO.findById(id);
        person.ifPresent(value -> model.addAttribute("person", value));

        List<Book> books = this.bookDAO.findByClientId(id);

        model.addAttribute("books", books);

        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("person", this.personDAO.findById(id).get());

        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        this.personDAO.delete(id);

        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (this.haveError(person, bindingResult)) {
            return "people/edit";
        }

        this.personDAO.update(id, person);

        return "redirect:/people";
    }

    private Boolean haveError(Person person, BindingResult bindingResult) {
        this.validator.validate(person, bindingResult);

        return bindingResult.hasErrors();
    }
}
