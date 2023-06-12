package com.practice.toby.ch1.factory;

import com.practice.toby.ch1.dao.UserDaoJdbc;
import com.practice.toby.ch4.dao.UserDao;
import com.practice.toby.ch5.service.UserServiceImpl;
import com.practice.toby.ch5.service.mail.DummyMailSender;
import com.practice.toby.ch6.service.UserService;
import com.practice.toby.ch6.service.UserServiceTx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Value("${spring.datasource.url}")
    String dataSourceUrl;

    @Bean
    public UserService userService() {
        return new UserServiceTx(userServiceImpl(), transactionManager());
    }
    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDao(),mailSender());
    }
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDaoJdbc(new JdbcTemplate(dataSource()));
        return userDao;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername("root");
        dataSource.setPassword("12341234");
        return dataSource;
    }
}
