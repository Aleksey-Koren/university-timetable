package com.foxminded.koren.university.repository.jdbcDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.LectureDao;
import com.foxminded.koren.university.repository.mappers.LectureMapper;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import static com.foxminded.koren.university.repository.sql.LectureSql.*;

@Repository
public class JdbcLectureDao implements LectureDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcLectureDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Lecture save(Lecture entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LOG.debug("Update database SQL = {} courseId = {} teacherId = {} audienceId = {} start = {} finish = {}",
                SAVE, entity.getCourse(), entity.getTeacher(), entity.getAudience(),
                entity.getStartTime(), entity.getEndTime());
        
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] { "id" });
                statement.setInt(1, entity.getCourse().getId());
                statement.setObject(2, entity.getTeacher() != null ? entity.getTeacher().getId() : null, 4);
                statement.setObject(3, entity.getAudience() != null ? entity.getAudience().getId() : null, 4);
                statement.setObject(4, entity.getStartTime());
                statement.setObject(5, entity.getEndTime());
                return statement;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New Lecture has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Lecture entity) {
        LOG.debug("Update database SQL: {} lecture.id = {}", UPDATE, entity.getId());

        try {
            jdbcTemplate.update(UPDATE, entity.getCourse().getId(),
                            entity.getTeacher() != null ? entity.getTeacher().getId() : null,
                            entity.getAudience() != null ? entity.getAudience().getId() : null,
                            entity.getStartTime(),
                            entity.getEndTime(),
                            entity.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        LOG.debug("Update database SQL: {} lecture.id = {}", DELETE, id);
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public Lecture getById(Integer id) {
        LOG.debug("Query to database SQL: {} lecture.id = {}", GET_BY_ID, id);
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new LectureMapper(), id);
        }catch(EmptyResultDataAccessException e){
            throw new RepositoryException("No such id in database", e);
        }
    }

    @Override
    public List<Lecture> getAll() {
        LOG.debug("Query to database SQL: {}", GET_ALL);
        return jdbcTemplate.query(GET_ALL, new LectureMapper());
    }

    @Override
    public List<Lecture> getTeacherLecturesByTimePeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        LOG.debug("Query to database SQL: {} teacher.id = {}, start: {} finish: {}",
                GET_BY_TEACHER_AND_TIME_PERIOD, teacher.getId(), start.atTime(0,0), finish.atTime(23,59,59));
        
        return jdbcTemplate.query(GET_BY_TEACHER_AND_TIME_PERIOD,
                                  new LectureMapper(), teacher.getId(),
                                  start.atTime(0,0),
                                  finish.atTime(23,59,59));
    }

    @Override
    public List<Lecture> getStudentLecturesByTimePeriod(Student student, LocalDate start, LocalDate finish) {
        LOG.debug("Query to database SQL: {} student.id = {}, start: {} finish: {}",
                GET_BY_STUDENT_AND_TIME_PERIOD, student.getId(), start.atTime(0,0), finish.atTime(23,59,59));
        
        return jdbcTemplate.query(GET_BY_TEACHER_AND_TIME_PERIOD,
                new LectureMapper(), student.getId(),
                start.atTime(0,0),
                finish.atTime(23,59,59));
    }

    @Override
    public boolean removeGroup(int lectureId, int groupId) {
        LOG.debug("Update to database. Remove group from lecture. SQL: {} lecture.id = {}, group.id = {}",
                "\n" + REMOVE_GROUP,lectureId, groupId);
        try {
            return jdbcTemplate.update(REMOVE_GROUP, lectureId, groupId) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addGroup(int lectureId, int groupId) {
        LOG.debug("Update to database. Add group to lecture. SQL: {} lecture.id = {}, group.id = {}",
                "\n" + ADD_GROUP,lectureId, groupId);
        try {
            return jdbcTemplate.update(ADD_GROUP, lectureId, groupId) == 1;
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}