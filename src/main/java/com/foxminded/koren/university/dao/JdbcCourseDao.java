package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.CourseDao;
import com.foxminded.koren.university.dao.mappers.CourseMapper;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

import static com.foxminded.koren.university.dao.sql.CourseSql.SAVE;
import static com.foxminded.koren.university.dao.sql.CourseSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.CourseSql.DELETE;
import static com.foxminded.koren.university.dao.sql.CourseSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.CourseSql.GET_ALL;
import static com.foxminded.koren.university.dao.sql.CourseSql.GET_BY_GROUP_ID;



@Repository
public class JdbcCourseDao implements CourseDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Course save(Course entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescrption());
                return statement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }
    
    @Override
    public void update(Course entity) {
        try {
            jdbcTemplate.update(UPDATE, entity.getName(), entity.getDescrption(), entity.getId());
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        
        try {
            return jdbcTemplate.update(DELETE, id) > 0;
        } catch (DataIntegrityViolationException e) {
            throw new DAOException (e.getMessage(), e);
        }
    }
   
    @Override
    public Course getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new CourseMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
    
    @Override
    public List<Course> getByGroup(Group group) {
        return jdbcTemplate.query(GET_BY_GROUP_ID, new CourseMapper(), group.getId());  
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(GET_ALL, new CourseMapper());
    }
}