package com.example.matchmaker.service;

import com.example.matchmaker.entity.User;

import java.util.List;

public interface UserGroupFactory {

    void createGroup(List<User> users);
}
