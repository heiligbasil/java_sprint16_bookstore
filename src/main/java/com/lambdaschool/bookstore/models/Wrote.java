package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.bookstore.logging.Loggable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Loggable
@Entity
@Table(name = "wrote",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"authorid", "bookid"})})
public class Wrote extends Auditable implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "authorid")
    @JsonIgnoreProperties("wrotes")
    private Author author;

    @Id
    @ManyToOne
    @JoinColumn(name = "bookid")
    @JsonIgnoreProperties("wrotes")
    private Book book;

    public Wrote()
    {
    }

    public Wrote(Author author,
                 Book book)
    {
        this.author = author;
        this.book = book;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public Book getBook()
    {
        return book;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Wrote))
        {
            return false;
        }
        Wrote wrote = (Wrote) o;
        return Objects.equals(getAuthor(),
                              wrote.getAuthor()) && Objects.equals(getBook(),
                                                                   wrote.getBook());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getAuthor(),
                            getBook());
    }
}

