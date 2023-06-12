package com.practice.toby.ch6.service;

import com.practice.toby.ch1.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@RequiredArgsConstructor
public class UserServiceTx implements UserService {

    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void add(User user) {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.add(user);
            this.transactionManager.commit(tx);

        } catch (Exception e) {
            this.transactionManager.rollback(tx);
            throw e;
        }
    }

    @Override
    public void upgradeLevels() throws SQLException {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.upgradeLevels();
            this.transactionManager.commit(tx);

        } catch (Exception e) {
            this.transactionManager.rollback(tx);
            throw e;
        }
    }
}
