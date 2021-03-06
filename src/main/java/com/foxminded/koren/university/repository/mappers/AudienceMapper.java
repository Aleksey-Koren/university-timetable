package com.foxminded.koren.university.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.entity.Audience;

public class AudienceMapper implements RowMapper<Audience> {

    @Override
    public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
        Audience audience = new Audience();
        audience.setId(rs.getInt("audience_id"));
        audience.setNumber(rs.getInt("audience_room_number"));
        audience.setCapacity(rs.getInt("audience_capacity"));
        return audience;
    }
}