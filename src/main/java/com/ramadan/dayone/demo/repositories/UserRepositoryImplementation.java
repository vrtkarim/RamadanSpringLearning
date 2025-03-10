package com.ramadan.dayone.demo.repositories;

import com.ramadan.dayone.demo.domain.User;
import com.ramadan.dayone.demo.exceptions.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class UserRepositoryImplementation implements UserRepository{
    private static final String sql_create = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
    private static final String sql_count = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String sql_find = "SELECT * FROM users WHERE user_id = ?";
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserRepositoryImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer create(String name, String email, String password) throws AuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql_create, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                return ps;
            }, keyHolder);
            return (Integer)keyHolder.getKeys().get("user_id");
        }catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) throws AuthException {
        return null;
    }

    @Override
    public Integer getCountByEmail(String email) throws AuthException {
        return jdbcTemplate.queryForObject(sql_count, new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer id) throws AuthException {
        return jdbcTemplate.queryForObject(sql_find,  new Object[]{id}, userRowMapper);
    }
    private final RowMapper<User>  userRowMapper= ((rs, rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password")
    ));
}
