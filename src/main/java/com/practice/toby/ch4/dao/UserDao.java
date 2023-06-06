package com.practice.toby.ch4.dao;

import com.practice.toby.ch1.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    int getCount();

    void update(User user);
}
