package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch1.factory.DaoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    @DisplayName("add와 get 테스트 코드")
    public void addAndGet() throws SQLException, ClassNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        User user1 = new User("myNewId", "name", "password");
        dao.add(user1);

        User user2 = dao.get(user1.getId());

        Assertions.assertThat(user1.getName()).isEqualTo(user2.getName());
        Assertions.assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }
}