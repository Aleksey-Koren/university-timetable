package com.foxminded.koren.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.domain.entity.Audience;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Lecture;
import com.foxminded.koren.university.domain.entity.Teacher;

public class LectureMapper implements RowMapper<Lecture> {

    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lecture lecture = new Lecture ();
        Course course = new Course(rs.getString("course_name"), rs.getString("course_description"));
        
        if(rs.getObject("teacher_id") != null) {
            Teacher teacher = new Teacher(rs.getString("teacher_first_name"),
                                          rs.getString("teacher_last_name"));
            teacher.setId(rs.getInt("teacher_id"));
            lecture.setTeacher(teacher);
        }
        
        if(rs.getObject("audience_id") != null) {
            Audience audience = new Audience(rs.getInt("audience_room_number"),
                                             rs.getInt("audience_capacity"));
            audience.setId(rs.getInt("udience_id"));
            lecture.setAudience(audience);
        }
        
        Timestamp startTimestamp = Timestamp.valueOf(rs.getString("start_time"));
        lecture.setStartTime(startTimestamp.toLocalDateTime());
        Timestamp endTimestamp = Timestamp.valueOf(rs.getString("end_time"));
        lecture.setEndTime(endTimestamp.toLocalDateTime());
        return lecture;
    }
}
