package com.practice.toby.ch6.service;

import com.practice.toby.ch1.domain.User;

import java.sql.SQLException;

public interface UserService {
    void add(User user);

    void upgradeLevels() throws SQLException ;
}
