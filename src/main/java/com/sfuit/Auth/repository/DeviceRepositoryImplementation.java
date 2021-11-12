package com.sfuit.Auth.repository;

import com.sfuit.Auth.entity.Devices;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtBadRequestException;
import com.sfuit.Auth.exceptions.EtResourceNotFoundException;
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
public class DeviceRepositoryImplementation implements DeviceRepository{

    private static final String SQL_FIND_BY_DECVICE_ID = "SELECT DEVICE_NUM, DEVICE_ID, SENSORS, DEVICE_ISVERIFIED FROM SFUIT_DEVICES WHERE DEVICE_ID = ?";
    private static final String SQL_FIND_BY_DECVICE_NUM = "SELECT DEVICE_NUM, DEVICE_ID, SENSORS, DEVICE_ISVERIFIED FROM SFUIT_DEVICES WHERE DEVICE_NUM = ?";
    private static final String SQL_COUNT_BY_DEVICE_ID= "SELECT COUNT(*) FROM SFUIT_DEVICES WHERE DEVICE_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO SFUIT_DEVICES (DEVICE_NUM, DEVICE_ID, SENSORS, " +
            "DEVICE_ISVERIFIED) VALUES(NEXTVAL('SFUIT_DEVICES_SEQ'), ?, ?, ?)";
    private  static final String SQL_UPDATE_ROW = "UPDATE SFUIT_DEVICES SET DEVICE_ISVERIFIED = 'true' WHERE DEVICE_ID = ? ";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Devices findByDeviceNum(Integer device_num) throws EtResourceNotFoundException {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_DECVICE_NUM, userRowMapper, new Object[]{device_num});
    }

    @Override
    public Devices findByDeviceId(String device_id) throws EtResourceNotFoundException {
        try
        {
            Devices devices = jdbcTemplate.queryForObject(SQL_FIND_BY_DECVICE_ID, userRowMapper, new Object[]{device_id});
            if(!device_id.equals(devices.getDevice_id()))
                throw new EtResourceNotFoundException("Invalid Device ID");
            jdbcTemplate.update(SQL_UPDATE_ROW, device_id);
            return devices;
        }catch (EmptyResultDataAccessException e)
        {
            throw new EtResourceNotFoundException("Invalid Device ID");
        }
    }

    @Override
    public Devices findByDeviceIdTemp(String device_id) {
        try {
            Devices devices = jdbcTemplate.queryForObject(SQL_FIND_BY_DECVICE_ID, userRowMapper, new Object[]{device_id});
            if (!device_id.equals(devices.getDevice_id()))
                throw new EtResourceNotFoundException("Invalid Device ID");
            return devices;
        }catch (EmptyResultDataAccessException e) {
            throw new EtResourceNotFoundException("Invalid Device ID");
        }
    }

    @Override
    public Integer create(String device_id, String sensors, String device_isverified) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps= connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, device_id);
                ps.setString(2, sensors);
                ps.setString(3, device_isverified);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("DEVICE_NUM");
        }catch (Exception e)
        {
            throw new EtBadRequestException(e.toString());
        }
    }

    @Override
    public Integer getCountByDeviceId(String device_id) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_DEVICE_ID, Integer.class, new Object[]{device_id});
    }

    private RowMapper<Devices> userRowMapper = ((rs, rowNum) -> {
        return new Devices(rs.getInt("DEVICE_NUM"),
                rs.getString("DEVICE_ID"),
                rs.getString("SENSORS"),
                rs.getString("DEVICE_ISVERIFIED"));
    });
}
