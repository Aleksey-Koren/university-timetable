package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.GroupDao;
import com.foxminded.koren.university.dao.mappers.GroupMapper;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

import static com.foxminded.koren.university.dao.sql.GroupSql.SAVE;
import static com.foxminded.koren.university.dao.sql.GroupSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.GroupSql.DELETE;
import static com.foxminded.koren.university.dao.sql.GroupSql.GET_GROUP_BY_ID;
import static com.foxminded.koren.university.dao.sql.GroupSql.ADD_COURSE;
import static com.foxminded.koren.university.dao.sql.GroupSql.REMOVE_COURSE;

@Repository
public class JdbcGroupDao implements GroupDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Group save(Group entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
                statement.setString(1, entity.getName());
                return statement;
        }, keyHolder);
                
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Group entity) {
        jdbcTemplate.update(UPDATE, entity.getName(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Group getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_GROUP_BY_ID, new GroupMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
    
    @Override
    public void addCourse(Group group, Course course) {
        jdbcTemplate.update(ADD_COURSE, group.getId(), course.getId());
    }
    @Override
    public boolean removeCourse(Group group, Course course) {
        return jdbcTemplate.update(REMOVE_COURSE, group.getId(), course.getId()) > 0;
    }
}