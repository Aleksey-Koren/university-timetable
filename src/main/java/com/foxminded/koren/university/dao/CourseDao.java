package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.Dao;
import com.foxminded.koren.university.dao.mappers.CourseMapper;
import com.foxminded.koren.university.dao.sql.CourseSql;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@Repository
public class CourseDao implements Dao<Integer, Course> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Course save(Course entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(CourseSql.getSave(), new String[] {"id"});
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescrption());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Course entity) {
        jdbcTemplate.update(CourseSql.getUpdate(), entity.getName(), entity.getDescrption(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        
        try {
            return jdbcTemplate.update(CourseSql.getDelete(), id) > 0;
        } catch (DataIntegrityViolationException e) {
            throw new DAOException (e.getMessage(), e);
        }
    }
   
    @Override
    public Course getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(CourseSql.getGetById(), new CourseMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
    
        public List<Course> getByGroup(Group group) {
            return jdbcTemplate.query(CourseSql.getGetByGroupId(), new CourseMapper(), group.getId());  
        }
}