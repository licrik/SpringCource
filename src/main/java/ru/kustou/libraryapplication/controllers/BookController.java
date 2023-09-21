package ru.kustou.libraryapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kustou.libraryapplication.dao.BookDAO;
import ru.kustou.libraryapplication.dao.PersonDAO;
import ru.kustou.libraryapplication.models.Book;
import ru.kustou.libraryapplication.models.BookWithFio;
import ru.kustou.libraryapplication.models.Person;
import ru.kustou.libraryapplication.utils.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator validator;

    @Autowired
    public BookController(BookDAO dao, PersonDAO personDAO, BookValidator validator) {
        this.bookDAO = dao;
        this.personDAO = personDAO;
        this.validator = validator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", this.bookDAO.getAll());

        return "books/index";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (this.haveError(book, bindingResult)) {
            return "books/new";
        }

        this.bookDAO.create(book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (this.haveError(book, bindingResult)) {
            return "books/edit";
        }

        this.bookDAO.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id) {
        this.bookDAO.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        BookWithFio book = this.bookDAO.findWithClientById(id).get();
        Boolean isFree = book.getFio() == null;

        model.addAttribute("book", book);
        model.addAttribute("isFree", isFree);

        if (isFree) {
            model.addAttribute("people", this.personDAO.getAll());
        }

        return "books/show";
    }

    @PatchMapping("/{id}/person")
    public String setPerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        this.bookDAO.updateClientById(id, person.getId());

        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String removePerson(@PathVariable int id) {
        this.bookDAO.freeBookBy(id);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("book", this.bookDAO.findById(id).get());

        return "books/edit";
    }

    private Boolean haveError(Book book, BindingResult bindingResult) {
        this.validator.validate(book, bindingResult);
        return bindingResult.hasErrors();
    }
}
