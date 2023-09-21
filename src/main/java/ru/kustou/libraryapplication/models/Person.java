package ru.kustou.libraryapplication.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Person {
    private int id;

    @NotEmpty(message = "FIO should be not empty")
    @Pattern(regexp = "([А-Я][а-я]+\\s*)*", message = "You FIO not correct")
    private String fio;

    @Min(value = 0, message = "Year should great than 0")
    private int year;

    public Person() {

    }

    public Person(int id, String fio, int year) {
        this.id = id;
        this.fio = fio;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
