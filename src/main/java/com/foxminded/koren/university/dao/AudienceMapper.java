package com.foxminded.koren.university.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.domain.entity.Audience;

public class AudienceMapper implements RowMapper<Audience> {

    @Override
    public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
        Audience audience = new Audience();
        audience.setId(rs.getInt("id"));
        audience.setNumber(rs.getInt("room_number"));
        audience.setCapacity(rs.getInt("capacity"));
        return audience;
    }
}