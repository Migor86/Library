package com.migor;

import java.util.List;
import java.util.Optional;

public interface UserStorage
{
    void add(User user);

    void remove(User user);

    Optional<User> findById(int id);

    List<User> findByName(String name);

    List<User> findAll();
}
