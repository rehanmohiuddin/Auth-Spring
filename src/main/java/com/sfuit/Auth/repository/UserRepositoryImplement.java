package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;
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

    private static final String SQL_CREATE = "INSERT INTO SFUIT_USERS(USER_ID, EMAIL, NAME, DOB, PHONE, OTP, IS_VERIFIED, TOKEN) VALUES(NEXTVAL('SFUIT_USERS_SEQ'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM SFUIT_USERS WHERE EMAIL = ?";
    private static final String SQL_COUNT_BY_PHONE = "SELECT COUNT(*) FROM SFUIT_USERS WHERE PHONE = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, EMAIL, NAME, DOB, PHONE, OTP, IS_VERIFIED, TOKEN " +
            "FROM SFUIT_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_PHONE = "SELECT USER_ID, EMAIL, NAME, DOB, PHONE, OTP, IS_VERIFIED, TOKEN " +
            "FROM SFUIT_USERS WHERE PHONE = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String email, String name, String dob, String phone, String otp, String is_verified, String token) throws EtAuthException {

        try{
            //for holding key value of id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            //calling jdbc update through lambda and keyholder

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,email);
                ps.setString(2,name);
                ps.setString(3,dob);
                ps.setString(4,phone);
                ps.setString(5,otp);
                ps.setString(6,is_verified);
                ps.setString(7,token);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch(Exception e)
        {
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByPhoneandOTP(String phone, String otp) throws EtAuthException {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_PHONE, userRowMapper, new Object[]{phone});
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid phone/otp");
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

    @Override
    public Integer getCountByPhone(String phone) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_PHONE, Integer.class, new Object[]{phone});
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                        rs.getString("EMAIL"),
                        rs.getString("NAME"),
                        rs.getString("DOB"),
                        rs.getString("PHONE"),
                        rs.getString("OTP"),
                        rs.getString("IS_VERIFIED"),
                        rs.getString("TOKEN"));
    });
}
