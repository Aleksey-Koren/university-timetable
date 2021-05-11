package com.foxminded.koren.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.domain.entity.Teacher;

public class TeacherMapper implements RowMapper<Teacher>{

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("teacher_id"));
        teacher.setFirstName(rs.getString("teacher_first_name"));
        teacher.setLastName(rs.getString("teacher_last_name"));
        return teacher;
    }
}