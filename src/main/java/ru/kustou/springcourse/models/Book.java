package ru.kustou.springcourse.models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    protected String name;

    @Column(name = "year")
    @Min(value = 0, message = "Year should great than 0")
    private int year;

    @Column(name = "author")
    @NotEmpty(message = "Author should be not empty")
    protected String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid", referencedColumnName = "id")
    private Person owner;

    @Column(name = "time_taken")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date timeTaken;

    @Transient
    private boolean isOverdue;

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return name + ", " + author + ", " + year;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }
}