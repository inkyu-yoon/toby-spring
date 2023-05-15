package com.practice.toby.ch1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException  {
        // 1. DB 연결을 위한 커넥션
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/toby-db", "root", "12341234");
        return c;
    }

}
