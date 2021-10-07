package com.foxminded.koren.university.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.foxminded.koren.university.entity.Year;
import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.entity.Group;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("group_id"));
        group.setName(rs.getString("group_name"));
        group.setYear(Year.valueOf(rs.getString("group_year")));
        return group;
    }
}