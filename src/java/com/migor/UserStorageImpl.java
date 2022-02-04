package com.migor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserStorageImpl implements UserStorage
{
    private final List<User> users;

    public UserStorageImpl()
    {
        users = new ArrayList<>();
    }

    @Override
    public void add(User user)
    {
        users.add(user);
    }

    @Override
    public void remove(User user)
    {
        users.remove(user);
    }

    @Override
    public Optional<User> findById(int id)
    {
        return users.stream()
                    .filter(u -> u.getId() == id)
                    .findAny();
    }

    @Override
    public List<User> findByName(String name)
    {
        return users.stream()
                    .filter(u -> u.getName().equals(name))
                    .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll()
    {
        return users;
    }
}
