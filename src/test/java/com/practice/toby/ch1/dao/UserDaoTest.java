package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

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

        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);

        assertThat(dao.getCount()).isEqualTo(1);

        User foundUser = dao.get(user1.getId());

        assertThat(user1.getName()).isEqualTo(foundUser.getName());
        assertThat(user1.getPassword()).isEqualTo(foundUser.getPassword());
    }

    @Test
    @DisplayName("getCount 테스트 코드")
    public void getCount() throws SQLException {

        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);


        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);

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
        assertThat(dao.getAll().size()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getAll().size()).isEqualTo(2);


        dao.add(user3);
        assertThat(dao.getAll().size()).isEqualTo(3);

        assertThat(dao.getAll().get(0).getName()).isEqualTo(user1.getName());
        assertThat(dao.getAll().get(1).getName()).isEqualTo(user2.getName());
        assertThat(dao.getAll().get(2).getName()).isEqualTo(user3.getName());
    }

    @Test
    @DisplayName("getAll 메서드 테스트(데이터가 없을 때)")
    public void getAllTest2() {

        assertTrue(dao.getAll().isEmpty());
        assertThat(dao.getAll().size()).isEqualTo(0);

    }

    @Test
    @DisplayName("중복 키 에러 테스트")
    public void duplicateKey() {
        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException rootCause = (SQLException) e.getRootCause();
            SQLErrorCodeSQLExceptionTranslator error = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(error.translate(null, null, rootCause))
                    .isInstanceOf(DuplicateKeyException.class);
        }
    }
}