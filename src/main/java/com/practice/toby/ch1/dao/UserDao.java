package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch3.jdbc.JdbcContext;
import com.practice.toby.ch3.statement.AddStatement;
import com.practice.toby.ch3.statement.DeleteAllStatement;
import com.practice.toby.ch3.statement.StatementStrategy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcContext jdbcContext;
    private DataSource dataSource;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }


    public void add(User user) {
        this.jdbcTemplate.update("INSERT INTO USERS(id,name,password) values(?,?,?)",user.getId(),user.getName(),user.getPassword());
    }



    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?",
                new Object[]{id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                        return user;
                    }
                });
    }

    public void deleteAll() throws SQLException {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users",Integer.class);

    }

}
