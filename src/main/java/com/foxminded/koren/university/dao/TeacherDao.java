package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.foxminded.koren.university.domain.entity.Teacher;

@Component
public class TeacherDao implements Dao<Integer, Teacher> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = 
            "INSERT INTO teacher (first_name, last_name)\r\n"
          + "VALUES (?, ?);";

  private static final String UPDATE = 
              "UPDATE teacher\r\n"
            + "SET first_name = ?,\r\n"
            + "    last_name = ?\r\n"
            + "WHERE id = ?;";

  private static final String DELETE = 
              "DELETE FROM teacher\r\n"
            + "WHERE id = ?;";

  private static final String GET_BY_ID = 
                 "SELECT id, first_name, last_name\r\n"
               + "FROM teacher\r\n"
               + "WHERE id = ?;";

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
    public Optional<Teacher> getById(Integer id) {
        List<Teacher> teachers = 
                jdbcTemplate.query(GET_BY_ID, new BeanPropertyRowMapper<Teacher>(Teacher.class), id);
        if(teachers.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(teachers.get(0));
        }
    }
}