package com.practice.toby.ch1.dao;

import com.practice.toby.ch1.domain.Level;
import com.practice.toby.ch1.domain.User;
import com.practice.toby.ch4.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"),
                    Level.valueOf(rs.getInt("level")), rs.getInt("login"), rs.getInt("recommend"));
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void add(User user) {
        this.jdbcTemplate.update("INSERT INTO USERS(id,name,password,level,login,recommend) values(?,?,?,?,?,?)", user.getId(), user.getName(),
                user.getPassword(),user.getLevel().getValue(),user.getLogin(),user.getRecommend());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?",
                new Object[]{id}, this.userRowMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);

    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ? , password = ? , level = ?, login = ?, " +
                        "recommend=? where id = ?",
                user.getName(), user.getPassword(), user.getLevel().getValue(), user.getLogin(), user.getRecommend(), user.getId());
    }


    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userRowMapper);
    }


}
