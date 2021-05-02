package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@Component
public class GroupDao implements Dao<Integer, Group> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = 
              "INSERT INTO group_table\r\n"
            + "(name)\r\n"
            + "VALUES\r\n"
            + "(?);";

    private static final String UPDATE = "UPDATE group_table\r\n"
              + "SET name = ?\r\n"
              + "WHERE id = ?;";

    private static final String DELETE = 
                "DELETE FROM group_table\r\n"
              + "WHERE id = ?;";

    private static final String GET_GROUP_BY_ID = 
                   "SELECT id, name\r\n"
                 + "FROM group_table\r\n"
                 + "WHERE id = ?;";
    
    private static final String GET_COURSES_OF_GROUP = 
              "SELECT c.id , c.name , c.description\r\n"
            + "FROM group_course gc\r\n"
            + "    JOIN course c ON gc.course_id = c.id\r\n"
            + "WHERE gc.group_id = ?;";
    
    private static final String ADD_COURSE = 
            "INSERT INTO group_course (group_id, course_id)\r\n"
          + "VALUES (?, ?);";

    private static final String REMOVE_ALL_COURSES = 
            "DELETE FROM group_course\r\n"
          + "WHERE group_id = ?;";
    
    
    @Override
    public void save(Group entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
                statement.setString(1, entity.getName());
                return statement;
        }, keyHolder);
                
        entity.setId((Integer) keyHolder.getKey());
        List<Course> courses = entity.getCourses();
        courses.forEach(s -> addCourseToGroup(entity, s));  
    }

    @Override
    public void update(Group entity) {
        int updatedElements = jdbcTemplate.update(UPDATE, entity.getName(), entity.getId());
        
        if(updatedElements == 1) {
            jdbcTemplate.update(REMOVE_ALL_COURSES, entity.getId());
            List<Course> courses = entity.getCourses();
            courses.forEach(s -> addCourseToGroup(entity, s)); 
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Optional<Group> getById(Integer id) {
        List<Group> result = jdbcTemplate.query(GET_GROUP_BY_ID, new GroupMapper(), id);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        
        List<Course> courses = jdbcTemplate.query(GET_COURSES_OF_GROUP, new CourseMapper(), id);
        if(courses.isEmpty()) {
            return Optional.of(result.get(0));
        }else {
            result.get(0).setCourses(courses);
            return Optional.of(result.get(0));
        }
    }
    
    private void addCourseToGroup(Group group, Course course) {
        jdbcTemplate.update(ADD_COURSE, group.getId(), course.getId());
    }
}