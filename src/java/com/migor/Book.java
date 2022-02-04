package com.migor;

import java.util.Objects;

public class Book
{
    private static int counter = 0;

    private final int id;
    private String author;
    private String name;

    public Book()
    {
        this.id = ++counter;
    }

    public int getId()
    {
        return id;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(author, book.author) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, author, name);
    }

    @Override
    public String toString()
    {
        return "Book{" +
               "id=" + id +
               ", author='" + author + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
