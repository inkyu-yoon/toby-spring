package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import com.practice.toby.ch5.service.TestUserService;
import com.practice.toby.ch5.service.UserServiceImpl;
import com.practice.toby.ch5.service.UserServiceException;
import com.practice.toby.ch5.service.mail.DummyMailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.practice.toby.ch1.domain.Level.*;
import static com.practice.toby.ch1.domain.UserConstants.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.practice.toby.ch1.domain.UserConstants.MIN_RECOMMEND_COUNT_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MailSender mailSender;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() throws SQLException {
        user1 = new User("id1", "name1", "p1", BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0, "email1");
        user2 = new User("id2", "name2", "p2", SILVER, 55, MIN_RECOMMEND_COUNT_FOR_GOLD, "email2");
        user3 = new User("id3", "name3", "p3", GOLD, 100, 40, "email3");
        dao.deleteAll();

    }

    @Test
    @DisplayName("add와 get 테스트 코드")
    public void addAndGet() throws SQLException, ClassNotFoundException {

        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);

        assertThat(dao.getCount()).isEqualTo(1);

        User foundUser = dao.get(user1.getId());

        checkSameUser(user1, foundUser);
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

    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void updateTest() {
        dao.add(user1);

        user1.setName("바꿈");
        user1.setPassword("바꿈");
        user1.setLogin(1000);
        user1.setLevel(GOLD);
        user1.setRecommend(999);

        dao.update(user1);

        User foundUser = dao.get(user1.getId());

        checkSameUser(user1, foundUser);
    }

    private void checkSameUser(User user, User foundUser) {

        assertThat(user.getName()).isEqualTo(foundUser.getName());
        assertThat(user.getPassword()).isEqualTo(foundUser.getPassword());
        assertThat(user.getLevel()).isEqualTo(foundUser.getLevel());
        assertThat(user.getLogin()).isEqualTo(foundUser.getLogin());
        assertThat(user.getRecommend()).isEqualTo(foundUser.getRecommend());
        assertThat(user.getEmail()).isEqualTo(foundUser.getEmail());
    }

    @Test
    @DisplayName("베이직 유저 등급 업그레이드 테스트")
    public void upgradeTest() throws SQLException {
        String basicUser1Id = "basic1";
        String basicUser2Id = "basic2";
        String basicUser3Id = "basic3";

        User basicUser1 = new User(basicUser1Id, "name", "password", BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0, "email1");
        User basicUser2 = new User(basicUser2Id, "name", "password", BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0, "email2");
        User basicUser3 = new User(basicUser3Id, "name", "password", BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 30, "email3");

        dao.add(basicUser1);
        dao.add(basicUser2);
        dao.add(basicUser3);

        userService.upgradeLevels();

        checkLevelUpgraded(basicUser1, false);
        checkLevelUpgraded(basicUser2, true);
        checkLevelUpgraded(basicUser3, false);

    }

    @Test
    @DisplayName("실버 유저 등급 업그레이드 테스트")
    public void upgradeTest2() throws SQLException {
        String silverUser1Id = "silver1";
        String silverUser2Id = "silver2";

        User silverUser1 = new User(silverUser1Id, "name", "password", SILVER, 40, MIN_RECOMMEND_COUNT_FOR_GOLD - 1,"email1");
        User silverUser2 = new User(silverUser2Id, "name", "password", SILVER, 50, MIN_RECOMMEND_COUNT_FOR_GOLD,"email2");

        dao.add(silverUser1);
        dao.add(silverUser2);

        userService.upgradeLevels();

        checkLevelUpgraded(silverUser1, false);
        checkLevelUpgraded(silverUser2, true);

    }

    @Test
    @DisplayName("골드 유저 등급 업그레이드 테스트")
    public void upgradeTest3() throws SQLException {
        String goldUserId = "gold";
        User goldUser = new User(goldUserId, "name", "password", GOLD, 40, 0,"email");

        dao.add(goldUser);

        userService.upgradeLevels();

        checkLevelUpgraded(goldUser, false);
    }

    private void checkLevelUpgraded(User user, boolean expectUpgrade) {
        User foundUser = dao.get(user.getId());
        //
        if (expectUpgrade) {
            assertThat(foundUser.getLevel()).isEqualTo(user.upgradeLevel().getLevel());
        } else {
            assertThat(foundUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    @DisplayName("회원 기본 등급 테스트")
    public void userBasicLevel() {
        User nullLevelUser = user1;
        nullLevelUser.setLevel(null);

        User silverUser = user2;
        User goldLevelUser = user3;

        userService.add(nullLevelUser);
        userService.add(silverUser);
        userService.add(goldLevelUser);

        User foundNullLevelUser = dao.get(nullLevelUser.getId());
        User foundSilverLevelUser = dao.get(silverUser.getId());
        User foundGoldLevelUser = dao.get(goldLevelUser.getId());

        assertThat(foundNullLevelUser.getLevel()).isEqualTo(BASIC);
        assertThat(foundSilverLevelUser.getLevel()).isEqualTo(SILVER);
        assertThat(foundGoldLevelUser.getLevel()).isEqualTo(GOLD);
    }


    @Test
    @DisplayName("업그레이드 중간에 회원 에러 발생 시")
    public void upgradeErrorTest() {
        TestUserService testUserService = new TestUserService(dao, mailSender, transactionManager, user2.getId());

        testUserService.setUserDao(dao);
        testUserService.setTransactionManager(transactionManager);
        testUserService.setMailSender(new DummyMailSender());
        
        testUserService.add(user1);
        testUserService.add(user2);
        testUserService.add(user3);


        assertThrows(UserServiceException.class, () -> testUserService.upgradeLevels());

        User foundUser = dao.get(user1.getId());

        assertThat(foundUser.getLevel()).isEqualTo(BASIC);

    }
}