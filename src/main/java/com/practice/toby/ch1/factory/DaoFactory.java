package com.practice.toby.ch1.factory;

import com.practice.toby.ch1.dao.UserDao;
import com.practice.toby.ch1.db.ConnectionMaker;
import com.practice.toby.ch1.db.CountingConnectionMaker;
import com.practice.toby.ch1.db.SimpleConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
