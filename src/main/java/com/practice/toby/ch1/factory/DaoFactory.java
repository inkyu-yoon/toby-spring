package com.practice.toby.ch1.factory;

import com.practice.toby.ch1.dao.UserDaoJdbc;
import com.practice.toby.ch4.dao.UserDao;
import com.practice.toby.ch5.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Value("${spring.datasource.url}")
    String dataSourceUrl;

    @Bean
    public UserService userService() {
        return new UserService(userDao(),dataSource());
    }
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDaoJdbc(new JdbcTemplate(dataSource()));
        return userDao;
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
