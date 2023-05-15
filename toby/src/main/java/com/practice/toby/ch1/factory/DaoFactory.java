package com.practice.toby.ch1.factory;

import com.practice.toby.ch1.dao.UserDao;
import com.practice.toby.ch1.db.ConnectionMaker;
import com.practice.toby.ch1.db.SimpleConnectionMaker;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    public ConnectionMaker getConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
