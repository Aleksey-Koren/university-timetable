package com.foxminded.koren.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.dao.mappers.LectureMapper;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;

import static com.foxminded.koren.university.dao.sql.LectureSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.LectureSql.GET_ALL;
import static com.foxminded.koren.university.dao.sql.LectureSql.SAVE;
import static com.foxminded.koren.university.dao.sql.LectureSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.LectureSql.DELETE;
import static com.foxminded.koren.university.dao.sql.LectureSql.GET_BY_TEACHER_AND_TIME_PERIOD;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcLectureDao implements LectureDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Lecture save(Lecture entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
            statement.setInt(1, entity.getCourse().getId());
            statement.setObject(2, entity.getTeacher() != null ? entity.getTeacher().getId() : null, 4);
            statement.setObject(3, entity.getAudience() != null ? entity.getAudience().getId() : null, 4);
            statement.setString(4, Timestamp.valueOf(entity.getStartTime()).toString());
            statement.setString(5, Timestamp.valueOf(entity.getEndTime()).toString());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Lecture entity) {
        jdbcTemplate.update(UPDATE, entity.getCourse().getId(),
                        entity.getTeacher() != null ? entity.getTeacher().getId() : null,
                        entity.getAudience() != null ? entity.getAudience().getId() : null,
                        Timestamp.valueOf(entity.getStartTime()).toString(),
                        Timestamp.valueOf(entity.getEndTime()).toString(),
                        entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Lecture getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new LectureMapper(), id);
        }catch(EmptyResultDataAccessException e){
            throw new DAOException("No such id in database", e);
        }
    }

    @Override
    public List<Lecture> getAll() {
        return jdbcTemplate.query(GET_ALL, new LectureMapper());
    }

    @Override
    public List<Lecture> getTeacherLecturesByTimePeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        return jdbcTemplate.query(GET_BY_TEACHER_AND_TIME_PERIOD,
                                  new LectureMapper(), teacher.getId(),
                                  start.atTime(0,0),
                                  finish.atTime(23,59,59));
    }

    @Override
    public List<Lecture> getStudentLecturesByTimePeriod(Student student, LocalDate start, LocalDate finish) {
        return jdbcTemplate.query(GET_BY_TEACHER_AND_TIME_PERIOD,
                new LectureMapper(), student.getId(),
                start.atTime(0,0),
                finish.atTime(23,59,59));
    }
}