package com.practice.toby.ch5.service;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import lombok.Setter;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;


@Setter
public class TestUserService extends UserServiceImpl {

    private UserDao userDao;
    private String id;

    private PlatformTransactionManager transactionManager;

    private MailSender mailSender;

    public TestUserService(UserDao userDao, MailSender mailSender, PlatformTransactionManager transactionManager, String id) {
        super(userDao, mailSender);
        this.id = id;
    }

    @Override
    public void upgradeLevels() throws SQLException {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());


        try {
            List<User> users = userDao.getAll();

            users.stream()
                    .filter(user -> canUpgradeLevel(user))
                    .forEach(user -> {
                        if (user.getId().equals(this.id)) {
                            throw new UserServiceException();
                        } else {
                            userDao.update(user.upgradeLevel());
                            sendUpgradeEmail(user);
                        }
                    });

            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new UserServiceException();
        }
    }
}
