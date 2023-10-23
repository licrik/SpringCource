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
import ru.kustou.springcourse.services.IBookService;
import ru.kustou.springcourse.services.IPersonService;
import ru.kustou.springcourse.utils.BookValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final IBookService bookService;
    private final IPersonService personService;
    private final BookValidator validator;

    @Autowired
    public BookController(BookDAO dao, PersonDAO personDAO, IBookService bookService, IPersonService personService, BookValidator validator) {
        this.bookDAO = dao;
        this.personDAO = personDAO;
        this.bookService = bookService;
        this.personService = personService;
        this.validator = validator;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(name="sort_by_year", required = false) boolean haveSort,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer size) {

        model.addAttribute("books", this.bookService.getAll(page, size, haveSort));

        return "books/index";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (this.haveError(book, bindingResult)) {
            return "books/new";
        }

        this.bookService.create(book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (this.haveError(book, bindingResult)) {
            return "books/edit";
        }

        this.bookService.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id) {
        this.bookService.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        Book book = this.bookService.findWithOwner(id);
        boolean isFree = book.getOwner() == null;

        model.addAttribute("book", book);
        model.addAttribute("isFree", isFree);

        if (isFree) {
            model.addAttribute("people", this.personService.getAll());
        }

        return "books/show";
    }

    @PatchMapping("/{id}/person")
    public String setPerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        this.bookService.updateOwner(id, this.personService.getById(person.getId()));

        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String removePerson(@PathVariable int id) {
        this.bookService.updateOwner(id, null);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("book", this.bookService.getById(id));

        return "books/edit";
    }

    @GetMapping("/search")
    public String searchPage(Model model,
                             @RequestParam(required = false, name = "keyword") String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("books", this.bookService.searchBook(keyword));
        }

        return "books/search";
    }

    private Boolean haveError(Book book, BindingResult bindingResult) {
        this.validator.validate(book, bindingResult);
        return bindingResult.hasErrors();
    }
}