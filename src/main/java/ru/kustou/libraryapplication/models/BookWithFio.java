package ru.kustou.libraryapplication.models;

public class BookWithFio extends Book {
    private String fio;

    public BookWithFio() {
        super();
    }

    public BookWithFio(int id, String name, int year, String author, String fio) {
        super(id, name, year, author);
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
