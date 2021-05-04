package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@Component
public class CourseDao implements Dao<Integer, Course> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = 
            "INSERT INTO course\r\n"
          + "(name, description)\r\n"
          + "VALUES\r\n"
          + "(?, ?);";
    
    private static final String UPDATE = 
            "UPDATE course\r\n"
          + "SET name = ?,\r\n"
          + "    description = ?\r\n"
          + "WHERE id = ?;";
    
    private static final String DELETE = 
            "DELETE FROM course\r\n"
          + "WHERE id = ?;";
    
    private static final String GET_BY_ID = 
            "SELECT id, name, description\r\n"
          + "FROM course\r\n"
          + "WHERE id = ?;";
    
    private static final String GET_BY_GROUP_ID = 
            "SELECT c.id , c.name , c.description\r\n"
          + "FROM group_course gc\r\n"
          + "    JOIN course c ON gc.course_id = c.id\r\n"
          + "WHERE gc.group_id = ?"
          + "ORDER BY id;";
    

    @Override
    public Course save(Course entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescrption());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Course entity) {
        jdbcTemplate.update(UPDATE, entity.getName(), entity.getDescrption(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {      
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Optional<Course> getById(Integer id) {
        List<Course> result = jdbcTemplate.query(GET_BY_ID, new CourseMapper(), id);
        if(result.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(result.get(0));     
        }
    }
    
//    @Override
//    public Optional<Course> getById(Integer id) {
//        try {
//            Course course = jdbcTemplate.queryForObject(GET_BY_ID, new CourseMapper(), id);
//            return Optional.of(course);
//        }catch(EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
    
        public List<Course> getByGroup(Group group) {
            return jdbcTemplate.query(GET_BY_GROUP_ID, new CourseMapper(), group.getId());  
        }
}