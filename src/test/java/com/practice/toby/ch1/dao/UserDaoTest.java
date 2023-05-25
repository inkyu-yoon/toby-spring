package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch1.factory.DaoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDaoTest {

    private UserDao dao;

    @BeforeEach
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    @DisplayName("add와 get 테스트 코드")
    public void addAndGet() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        Assertions.assertThat(dao.getCount()).isEqualTo(0);

        User user1 = new User("myNewId", "name", "password");
        dao.add(user1);

        Assertions.assertThat(dao.getCount()).isEqualTo(1);

        User user2 = dao.get(user1.getId());

        Assertions.assertThat(user1.getName()).isEqualTo(user2.getName());
        Assertions.assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    @DisplayName("getCount 테스트 코드")
    public void getCount() throws SQLException {
        dao.deleteAll();

        Assertions.assertThat(dao.getCount()).isEqualTo(0);

        dao.add(new User("id1", "name", "p"));
        Assertions.assertThat(dao.getCount()).isEqualTo(1);

        dao.add(new User("id2", "name", "p"));
        Assertions.assertThat(dao.getCount()).isEqualTo(2);


        dao.add(new User("id3", "name", "p"));
        Assertions.assertThat(dao.getCount()).isEqualTo(3);

    }

    @Test
    @DisplayName("get 메서드 실행 시, 존재하지 않는 경우 예외 발생")
    public void getError() {

        assertThrows(EmptyResultDataAccessException.class, () -> dao.get("random"));
    }
}