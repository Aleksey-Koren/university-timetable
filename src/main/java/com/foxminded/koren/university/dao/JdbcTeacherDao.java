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
import com.foxminded.koren.university.dao.interfaces.TeacherDao;
import com.foxminded.koren.university.domain.entity.Teacher;

import static com.foxminded.koren.university.dao.sql.TeacherSql.SAVE;
import static com.foxminded.koren.university.dao.sql.TeacherSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.TeacherSql.DELETE;
import static com.foxminded.koren.university.dao.sql.TeacherSql.GET_BY_ID;

@Repository
public class JdbcTeacherDao implements TeacherDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Teacher save(Teacher entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Teacher entity) {
        jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getId());        
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Teacher getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new BeanPropertyRowMapper<>(Teacher.class), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
}