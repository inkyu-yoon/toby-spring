package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch1.factory.DaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
        User user1 = new User("1", "name", "password");
        dao.add(user1);

        User user2 = dao.get(user1.getId());

        if (!user1.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 name");
        } else if (!user1.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 password");
        } else {
            System.out.println("조회 테스트 성공");
        }

    }
}
