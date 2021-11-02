package com.sfuit.Auth.repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImplement implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO SFUIT_USERS(USER_ID, EMAIL, NAME, PASSWORD, DOB, PHONE, OTP, IS_VERIFIED) VALUES(NEXTVAL('SFUIT_USERS_SEQ'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM SFUIT_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, EMAIL, NAME, PASSWORD, DOB, PHONE, OTP, IS_VERIFIED " +
            "FROM SFUIT_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, EMAIL, NAME, PASSWORD, DOB, PHONE, OTP, IS_VERIFIED " +
            "FROM SFUIT_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String email, String name, String password, String dob, String phone, String otp, String is_verified) throws EtAuthException {

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            //for holding key value of id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            //calling jdbc update through lambda and keyholder

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,email);
                ps.setString(2,name);
                ps.setString(3,hashedPassword);
                ps.setString(4,dob);
                ps.setString(5,phone);
                ps.setString(6,otp);
                ps.setString(7,is_verified);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch(Exception e)
        {
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, new Object[]{email});
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new EtAuthException("Invalid email/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, new Object[]{email});
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, new Object[]{userId});
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                        rs.getString("EMAIL"),
                        rs.getString("NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("DOB"),
                        rs.getString("PHONE"),
                        rs.getString("OTP"),
                        rs.getString("IS_VERIFIED")
                        );
    });
}
