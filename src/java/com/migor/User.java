package com.migor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class User
{
    private static int counter = 0;

    private final int id;
    private final String name;
    private final List<Book> books;

    public User(String userName)
    {
        this.id = ++counter;
        this.name = userName;
        books = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void addBook(Book book)
    {
        books.add(book);
    }

    public void removeBook(Book book)
    {
        books.remove(book);
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public Optional<Book> findBookById(int id)
    {
        return books.stream()
                    .filter(b -> b.getId() == id)
                    .findAny();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(books, user.books);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, books);
    }

    @Override
    public String toString()
    {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
