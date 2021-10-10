package com.foxminded.koren.university.repository.jdbcDao;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.CourseRepository;
import com.foxminded.koren.university.repository.mappers.CourseMapper;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

import static com.foxminded.koren.university.repository.sql.CourseSql.*;

@Repository
public class JdbcCourseDao implements CourseRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCourseDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course save(Course entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LOG.debug("Update database. Save new course  SQL = {} course: {}",
                SAVE, entity);
        
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                return statement;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }

        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New Course has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }
    
    @Override
    public void update(Course entity) {
        LOG.debug("Update database. Update Course SQL: {} audience {}", UPDATE, entity);
        try {
            jdbcTemplate.update(UPDATE, entity.getName(), entity.getDescription(), entity.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            LOG.debug("Update database. Delete course by id. SQL: {} course.id = {}", DELETE, id);
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
   
    @Override
    public Course getById(Integer id) {
        LOG.debug("Query to database. Get course by id. SQL: {} course.id = {}", GET_BY_ID, id);

        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new CourseMapper(), id);
        }catch(DataAccessException e) {
            throw new RepositoryException(String
                    .format("Unable to delete course with id = %s, cause: there is no course with such id in database", id),
                    e);
        }
    }

    @Override
    public List<Course> getAll() {
        LOG.debug("Query to database. Get all courses. SQL: {}", GET_ALL);
        try {
            return jdbcTemplate.query(GET_ALL, new CourseMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}