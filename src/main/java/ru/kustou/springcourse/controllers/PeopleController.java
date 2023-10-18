package ru.kustou.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kustou.springcourse.dao.BookDAO;
import ru.kustou.springcourse.dao.PersonDAO;
import ru.kustou.springcourse.models.Book;
import ru.kustou.springcourse.models.Person;
import ru.kustou.springcourse.services.IPersonService;
import ru.kustou.springcourse.utils.PersonValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final IPersonService personService;
    private final PersonValidator validator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, IPersonService personService, PersonValidator validator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personService = personService;
        this.validator = validator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", this.personService.getAll());

        return "people/index";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (this.haveError(person, bindingResult)) {
            return "people/new";
        }

        this.personService.create(person);

        return "redirect:/people";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person")Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable int id, Model model) {
        Person person = this.personService.getByIdWithBooks(id);
        model.addAttribute("person", person);

        model.addAttribute("books", person.getBooks());

        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("person", this.personService.getById(id));

        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        this.personService.delete(id);

        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (this.haveError(person, bindingResult)) {
            return "people/edit";
        }

        this.personService.update(id, person);

        return "redirect:/people";
    }

    private Boolean haveError(Person person, BindingResult bindingResult) {
        this.validator.validate(person, bindingResult);

        return bindingResult.hasErrors();
    }
}