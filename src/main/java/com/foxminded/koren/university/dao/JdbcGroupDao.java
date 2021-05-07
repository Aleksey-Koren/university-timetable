package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.CrudDao;
import com.foxminded.koren.university.dao.interfaces.GroupDao;
import com.foxminded.koren.university.dao.mappers.GroupMapper;
import com.foxminded.koren.university.dao.sql.GroupSql;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@Repository
public class JdbcGroupDao implements GroupDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Group save(Group entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(GroupSql.getSave(), new String[] {"id"});
                statement.setString(1, entity.getName());
                return statement;
        }, keyHolder);
                
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Group entity) {
        jdbcTemplate.update(GroupSql.getUpdate(), entity.getName(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(GroupSql.getDelete(), id) > 0;
    }

    @Override
    public Group getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GroupSql.getGetGroupById(), new GroupMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
    
    @Override
    public void addCourse(Group group, Course course) {
        jdbcTemplate.update(GroupSql.getAddCourse(), group.getId(), course.getId());
    }
    @Override
    public boolean removeCourse(Group group, Course course) {
        return jdbcTemplate.update(GroupSql.getRemoveCourse(), group.getId(), course.getId()) > 0;
    }
}