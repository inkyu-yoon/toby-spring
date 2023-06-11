package com.practice.toby.ch1.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.practice.toby.ch1.domain.Level.*;
import static com.practice.toby.ch1.domain.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() throws SQLException {
        user1 = new User("id1", "name1", "p1", BASIC, 51, 0, "email1");
        user2 = new User("id2", "name2", "p2", SILVER, 55, 40,"email2");
        user3 = new User("id3", "name3", "p3", GOLD, 100, 40,"email3");

    }

    @Test
    @DisplayName("회원 등업 테스트")
    public void upgradeSuccessTest() {
        User user1AfterUpgrade = user1.upgradeLevel();
        User user2AfterUpgrade = user2.upgradeLevel();
        User user3AfterUpgrade = user3.upgradeLevel();

        Assertions.assertThat(user1AfterUpgrade.getLevel()).isEqualTo(SILVER);
        Assertions.assertThat(user2AfterUpgrade.getLevel()).isEqualTo(GOLD);
        Assertions.assertThat(user3AfterUpgrade.getLevel()).isEqualTo(GOLD);

    }

    @Test
    @DisplayName("회원 기본 레벨 세팅 테스트")
    public void nullLevelUser() {
        user1.setLevel(null);

        user1.validateAndSetDefaultLevel();

        Assertions.assertThat(user1.getLevel()).isEqualTo(BASIC);

    }

    @Test
    @DisplayName("회원 등업 조건 테스트")
    public void upgradeConditionTest() {
        boolean basicUser = user1.canUpgradeLevelBasic(MIN_LOGIN_COUNT_FOR_SILVER);
        boolean silverUser = user2.canUpgradeLevelSilver(MIN_RECOMMEND_COUNT_FOR_GOLD);

        Assertions.assertThat(basicUser).isTrue();
        Assertions.assertThat(silverUser).isTrue();

        user1.setLogin(0);
        user2.setRecommend(0);

        basicUser = user1.canUpgradeLevelBasic(MIN_LOGIN_COUNT_FOR_SILVER);
        silverUser = user2.canUpgradeLevelSilver(MIN_RECOMMEND_COUNT_FOR_GOLD);

        Assertions.assertThat(basicUser).isFalse();
        Assertions.assertThat(silverUser).isFalse();
    }
}