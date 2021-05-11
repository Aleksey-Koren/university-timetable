package com.foxminded.koren.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.domain.entity.Group;
import com.foxminded.koren.university.domain.entity.Student;
import com.foxminded.koren.university.domain.entity.Year;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setYear(Year.valueOf(rs.getString("student_year")));
                
        if(rs.getObject("group_id") != null ) {
            student.setGroup(new GroupMapper().mapRow(rs, rowNum));
        }
      
        return student;
    }
}