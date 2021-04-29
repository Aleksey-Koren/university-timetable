package com.foxminded.koren.university.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.foxminded.koren.university.domain.entity.Course;

@Component
public class CourseDao implements Dao<Integer, Course> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = "INSERT INTO course\r\n"
                                     + "(name, description)\r\n"
                                     + "VALUES\r\n"
                                     + "(?, ?);";
    
    private static final String UPDATE = "UPDATE course\r\n"
                                       + "SET name = ?,\r\n"
                                       + "description = ?\r\n"
                                       + "WHERE id = ?;";
    
    private static final String DELETE = "DELETE FROM course\r\n"
                                       + "WHERE id = ?;";
    
    private static final String GET_BY_ID = "SELECT id, name, description\r\n"
                                    + "FROM course\r\n"
                                    + "WHERE id = ?;";
    

    @Override
    public void save(Course entity) {
        jdbcTemplate.update(SAVE, entity.getName(), entity.getDescrption());
    }

    @Override
    public void update(Course entity) {
        jdbcTemplate.update(UPDATE, entity.getName(), entity.getDescrption(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {      
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    public Optional<Course> getById(Integer id) {
        List<Course> result = jdbcTemplate.query(GET_BY_ID, new CourseMapper(), id);
        if(result.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(result.get(0));     
        }
    }
    
//    @Override
//    public Course getById(Integer id) {
//        return jdbcTemplate.queryForObject(GET, new CourseMapper(), id);
//    }
}
