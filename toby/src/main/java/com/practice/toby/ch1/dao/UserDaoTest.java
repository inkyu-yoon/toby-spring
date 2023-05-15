package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.factory.DaoFactory;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDao userDao = new DaoFactory().userDao();
    }
}
