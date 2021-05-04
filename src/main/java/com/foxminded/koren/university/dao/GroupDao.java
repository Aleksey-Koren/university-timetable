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
        
    private static final String ADD_COURSE = 
            "INSERT INTO group_course (group_id, course_id)\r\n"
          + "VALUES (?, ?);";

    private static final String REMOVE_COURSE = 
            "DELETE FROM group_course\r\n"
          + "WHERE group_id = ?\r\n"
          + "AND course_id = ?;";
    
    
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
    public Optional<Group> getById(Integer id) {
        List<Group> result = jdbcTemplate.query(GET_GROUP_BY_ID, new GroupMapper(), id);
        if(result.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(result.get(0));
        }
    }
    
    public void addCourse(Group group, Course course) {
        jdbcTemplate.update(ADD_COURSE, group.getId(), course.getId());
    }
    
    public boolean removeCourse (Group group, Course course) {
        return jdbcTemplate.update(REMOVE_COURSE, group.getId(), course.getId()) > 0;
    }
    

}