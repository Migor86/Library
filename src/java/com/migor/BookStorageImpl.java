package com.migor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookStorageImpl implements BookStorage
{
    private final List<Book> books;

    public BookStorageImpl()
    {
        books = new ArrayList<>();
    }

    @Override
    public void add(Book book)
    {
        books.add(book);
    }

    @Override
    public void remove(Book book)
    {
        books.remove(book);
    }

    @Override
    public Optional<Book> findById(int id)
    {
        return books.stream()
                    .filter(b -> b.getId() == id)
                    .findAny();
    }

    @Override
    public List<Book> findByName(String name)
    {
        return books.stream()
                    .filter(b -> b.getName().equals(name))
                    .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author)
    {
        return books.stream()
                    .filter(b -> b.getAuthor().equals(author))
                    .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll()
    {
        return books;
    }
}
