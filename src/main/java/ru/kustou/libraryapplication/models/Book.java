package ru.kustou.libraryapplication.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    protected int id;

    @NotEmpty(message = "Name should be not empty")
    protected String name;

    @Min(value = 0, message = "Year should great than 0")
    private int year;

    @NotEmpty(message = "Author should be not empty")
    protected String author;

    public Book() {

    }

    public Book(int id, String name, int year, String author) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
