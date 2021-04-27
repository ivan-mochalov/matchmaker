package com.example.matchmaker.control.repository.impl;

import com.example.matchmaker.control.repository.UserRepository;
import com.example.matchmaker.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User findBy(String name) {
        return users.get(name);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User deleteBy(String name) {
        return users.remove(name);
    }
}
