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
        Group group = null;
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setYear(Year.valueOf(rs.getString("student_year")));
        
        Integer groupID = rs.getObject("group_id", Integer.class);
        String groupName =rs.getObject("group_name", String.class);
        
        if(groupID != null && groupName != null) {
            group = new Group(groupName);
            group.setId(groupID);
        }
        
        student.setGroup(group);
        return student;
    }
}