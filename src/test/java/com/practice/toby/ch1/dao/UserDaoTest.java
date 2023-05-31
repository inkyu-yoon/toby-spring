package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserDaoTest {

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() throws SQLException {
        user1 = new User("id1", "name1", "p1");
        user2 = new User("id2", "name2", "p2");
        user3 = new User("id3", "name3", "p3");
        dao.deleteAll();

    }

    @Test
    @DisplayName("add와 get 테스트 코드")
    public void addAndGet() throws SQLException, ClassNotFoundException {

        Assertions.assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);

        Assertions.assertThat(dao.getCount()).isEqualTo(1);

        User foundUser = dao.get(user1.getId());

        Assertions.assertThat(user1.getName()).isEqualTo(foundUser.getName());
        Assertions.assertThat(user1.getPassword()).isEqualTo(foundUser.getPassword());
    }

    @Test
    @DisplayName("getCount 테스트 코드")
    public void getCount() throws SQLException {

        Assertions.assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        Assertions.assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        Assertions.assertThat(dao.getCount()).isEqualTo(2);


        dao.add(user3);
        Assertions.assertThat(dao.getCount()).isEqualTo(3);

    }

    @Test
    @DisplayName("get 메서드 실행 시, 존재하지 않는 경우 예외 발생")
    public void getError() {

        assertThrows(EmptyResultDataAccessException.class, () -> dao.get("random"));
    }


    @Test
    @DisplayName("getAll 메서드 테스트")
    public void getAllTest() {

        dao.add(user1);
        Assertions.assertThat(dao.getAll().size()).isEqualTo(1);

        dao.add(user2);
        Assertions.assertThat(dao.getAll().size()).isEqualTo(2);


        dao.add(user3);
        Assertions.assertThat(dao.getAll().size()).isEqualTo(3);

        Assertions.assertThat(dao.getAll().get(0).getName()).isEqualTo(user1.getName());
        Assertions.assertThat(dao.getAll().get(1).getName()).isEqualTo(user2.getName());
        Assertions.assertThat(dao.getAll().get(2).getName()).isEqualTo(user3.getName());
    }
    @Test
    @DisplayName("getAll 메서드 테스트(데이터가 없을 때)")
    public void getAllTest2() {

        assertTrue(dao.getAll().isEmpty());
        Assertions.assertThat(dao.getAll().size()).isEqualTo(0);

    }
}