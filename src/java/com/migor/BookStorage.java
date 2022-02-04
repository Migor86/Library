package com.migor;

import java.util.List;
import java.util.Optional;

public interface BookStorage
{
    void add(Book book);

    void remove(Book book);

    Optional<Book> findById(int id);

    List<Book> findAll();

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);

}
