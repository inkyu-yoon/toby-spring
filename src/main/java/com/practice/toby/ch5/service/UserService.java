package com.practice.toby.ch5.service;


import com.practice.toby.ch1.domain.Level;
import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch1.domain.UserConstants;
import com.practice.toby.ch4.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.practice.toby.ch1.domain.UserConstants.*;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final PlatformTransactionManager transactionManager;


    // Basic 멤버가 login 횟수가 50이상이면 실버로, 실버인 멤버가 추천 30 이상이면 골드로 그 외는 변경 없음
    public void upgradeLevels() throws SQLException {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());


        try {
            List<User> users = userDao.getAll();

            users.stream()
                    .filter(user -> canUpgradeLevel(user))
                    .forEach(user -> userDao.update(user.upgradeLevel()));

            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new RuntimeException(e);
        }
    }


    public boolean canUpgradeLevel(User user) {

        return user.canUpgradeLevelBasic(MIN_LOGIN_COUNT_FOR_SILVER) || user.canUpgradeLevelSilver(MIN_RECOMMEND_COUNT_FOR_GOLD);
    }

    public void add(User user) {

        user.validateAndSetDefaultLevel();

        userDao.add(user);

    }
}
