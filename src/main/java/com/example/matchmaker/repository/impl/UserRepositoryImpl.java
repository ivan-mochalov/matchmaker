package com.example.matchmaker.repository.impl;

import com.example.matchmaker.repository.UserRepository;
import com.example.matchmaker.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
        log.debug("User [{}] saved", user.getName());
    }

    @Override
    public User findBy(String name) {
        User user = users.get(name);
        log.debug("User [{}] {}", name, user != null ? "retrieved" : "not found");
        return user;
    }

    @Override
    public List<User> findAll() {
        log.debug("Retrieving all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User deleteBy(String name) {
        User user = users.remove(name);
        log.debug("User [{}] {}", name, user != null ? "removed" : "not found");
        return user;
    }
}
