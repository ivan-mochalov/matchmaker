package com.example.matchmaker.repository;

import com.example.matchmaker.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findBy(String name);

    List<User> findAll();

    User deleteBy(String name);
}
