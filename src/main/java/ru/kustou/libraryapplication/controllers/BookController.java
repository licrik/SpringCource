package ru.kustou.libraryapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kustou.libraryapplication.dao.BookDAO;
import ru.kustou.libraryapplication.models.Book;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO dao;

    @Autowired
    public BookController(BookDAO dao) {
        this.dao = dao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", this.dao.getAll());

        return "books/index";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (this.haveError(book, bindingResult)) {
            return "books/new";
        }

        this.dao.create(book);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    private Boolean haveError(Book book, BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }
}
