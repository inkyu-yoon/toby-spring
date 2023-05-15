package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;

import java.sql.*;

public abstract class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection c = getConnection();

        // 2. Statement를 만들고 실행
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO USERS(id,name,password) values(?,?,?)"
        );

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // 3. 작업이 끝난 뒤, 리소스 반환
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement(
                    "SELECT * FROM USERS WHERE id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
