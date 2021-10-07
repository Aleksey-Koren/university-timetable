package com.foxminded.koren.university.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.entity.Student;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
                
        if(rs.getObject("group_id") != null ) {
            student.setGroup(new GroupMapper().mapRow(rs, rowNum));
        }
      
        return student;
    }
}