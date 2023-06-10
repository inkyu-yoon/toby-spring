package com.practice.toby.ch5.service;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import lombok.Setter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Setter
public class TestUserService extends UserService {

    private UserDao userDao;
    private String id;
    private DataSource dataSource;


    public TestUserService(UserDao userDao, DataSource dataSource, String id) {
        super(userDao, dataSource);
        this.id = id;
    }

    @Override
    public void upgradeLevels() throws SQLException {
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);


        try {
            List<User> users = userDao.getAll();

            users.stream()
                    .filter(user -> canUpgradeLevel(user))
                    .forEach(user -> {
                        if (user.getId().equals(this.id)) {
                            throw new UserServiceException();
                        } else {
                            userDao.update(user.upgradeLevel());
                        }
                    });

            c.commit();
        } catch (Exception e) {
            c.rollback();
            throw new UserServiceException();
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

    }
}
