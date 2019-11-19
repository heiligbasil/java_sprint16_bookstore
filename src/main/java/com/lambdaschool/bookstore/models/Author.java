package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.bookstore.logging.Loggable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "author",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"fname", "lname"})})
public class Author extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnoreProperties("authorid")
    private long authorid;

    private String fname;
    private String lname;

    @OneToMany(mappedBy = "author",
               cascade = CascadeType.ALL)
    @JsonIgnoreProperties("author")
    private List<Wrote> wrotes = new ArrayList<>();

    public Author()
    {
    }

    public Author(String fname,
                  String lname)
    {
        this.fname = fname;
        this.lname = lname;
    }

    public long getAuthorid()
    {
        return authorid;
    }

    public void setAuthorid(long authorid)
    {
        this.authorid = authorid;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public List<Wrote> getWrotes()
    {
        return wrotes;
    }

    public void setWrotes(List<Wrote> wrotes)
    {
        this.wrotes = wrotes;
    }
}
