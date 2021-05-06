package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.Dao;
import com.foxminded.koren.university.dao.sql.TeacherSql;
import com.foxminded.koren.university.domain.entity.Teacher;

@Repository
public class TeacherDao implements Dao<Integer, Teacher> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Teacher save(Teacher entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(TeacherSql.getSave(), new String[] {"id"});
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Teacher entity) {
        jdbcTemplate.update(TeacherSql.getUpdate(), entity.getFirstName(), entity.getLastName(), entity.getId());        
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(TeacherSql.getDelete(), id) > 0;
    }

    @Override
    public Teacher getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(TeacherSql.getGetById(), new BeanPropertyRowMapper<>(Teacher.class), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
}