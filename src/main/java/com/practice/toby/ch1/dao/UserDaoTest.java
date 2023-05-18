package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.db.CountingConnectionMaker;
import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch1.factory.DaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.add(new User("id1","name","password"));
        dao.add(new User("id2","name","password"));
        dao.add(new User("id3","name","password"));

    }
}
