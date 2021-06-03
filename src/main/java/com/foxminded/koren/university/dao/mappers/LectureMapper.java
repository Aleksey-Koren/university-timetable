package com.foxminded.koren.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

public class LectureMapper implements RowMapper<TimetableEvent> {

    @Override
    public TimetableEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimetableEvent lecture = new Lecture ();
        lecture.setCourse(new CourseMapper().mapRow(rs, rowNum));
        
        if(rs.getObject("teacher_id") != null) {
            lecture.setTeacher(new TeacherMapper().mapRow(rs, rowNum));
        }
        
        if(rs.getObject("audience_id") != null) {
            lecture.setAudience(new AudienceMapper().mapRow(rs, rowNum));
        }
        
        Timestamp startTimestamp = Timestamp.valueOf(rs.getString("start_time"));
        lecture.setStartTime(startTimestamp.toLocalDateTime());
        Timestamp endTimestamp = Timestamp.valueOf(rs.getString("end_time"));
        lecture.setEndTime(endTimestamp.toLocalDateTime());
        lecture.setId(rs.getInt("lecture_id"));
        return lecture;
    }
}