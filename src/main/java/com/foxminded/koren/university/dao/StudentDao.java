package com.foxminded.koren.university.dao;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.Dao;
import com.foxminded.koren.university.dao.mappers.StudentMapper;
import com.foxminded.koren.university.domain.entity.Student;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class StudentDao implements Dao<Integer, Student> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
//    @Autowired
//    GroupDao groupDao;
    
    private static final String SAVE = 
            "INSERT INTO student\r\n"
          + "(group_id, first_name, last_name, student_year)\r\n"
          + "VALUES\r\n"
          + "(?, ?, ?, ?);";
    
    private static final String GET_BY_ID = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       s.student_year,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "WHERE s.id = ?;";
    
    private static final String UPDATE = 
            "UPDATE student \r\n"
          + "SET group_id = ?,\r\n"
          + "    first_name = ?,\r\n"
          + "    last_name = ?,\r\n"
          + "    student_year = ?\r\n"
          + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID =
            "DELETE FROM student\r\n"
          + "WHERE id = ?;";
    

    
    
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
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
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