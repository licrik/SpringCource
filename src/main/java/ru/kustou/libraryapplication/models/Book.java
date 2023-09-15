package ru.kustou.libraryapplication.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @NotEmpty(message = "Name should be not empty")
    private String name;

    @Min(value = 0, message = "Year should great than 0")
    private int year;

    @NotEmpty(message = "Author should be not empty")
    private String author;

    private int clientId;

    public Book() {

    }

    public Book(int id, String name, int year, @NotEmpty(message = "Author should be not empty") String author) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.author = author;
    }

    public Book(int id, String name, int year, @NotEmpty(message = "Author should be not empty") String author, int clientId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.author = author;
        this.clientId = clientId;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
