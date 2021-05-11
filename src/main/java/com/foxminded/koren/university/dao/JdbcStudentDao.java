package com.foxminded.koren.university.dao;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.dao.mappers.StudentMapper;
import com.foxminded.koren.university.domain.entity.Student;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static com.foxminded.koren.university.dao.sql.StudentSql.SAVE;
import static com.foxminded.koren.university.dao.sql.StudentSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.StudentSql.DELETE;
import static com.foxminded.koren.university.dao.sql.StudentSql.GET_BY_ID;

@Repository
public class JdbcStudentDao implements StudentDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    @Override
    public Student save(Student entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[]{"id"});
            if(entity.getGroup() != null) {
                statement.setInt(1, entity.getGroup().getId());
            }else {
                statement.setObject(1, null);
            }
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getYear().toString());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Student entity) {
        jdbcTemplate.update(UPDATE, 
                entity.getGroup() != null ? entity.getGroup().getId() : null, 
                entity.getFirstName(),
                entity.getLastName(),
                entity.getYear().toString(),
                entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Student getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new StudentMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
}