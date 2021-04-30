package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.koren.university.SpringConfig;
import com.foxminded.koren.university.domain.entity.Course;

@TestInstance(Lifecycle.PER_CLASS)
class CourseDaoTest {
    
    AnnotationConfigApplicationContext context;
    
    private TablesCreation tablesCreation;
    
    private JdbcTemplate jdbcTemplate;
    
    private CourseDao courseDao;
    
    @BeforeAll
    void contextInit() {
        context = new AnnotationConfigApplicationContext(SpringConfig.class);
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        courseDao = context.getBean("courseDao", CourseDao.class);
    }
    
    @AfterAll
    void closeContext() {    
        context.close();
    }
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }

    @Test
    void getById_shouldWorkCorrectly() {
        String sql = "INSERT INTO course\r\n"
                   + "(id, name, description)\r\n"
                   + "VALUES\r\n"
                   + "(5, 'name', 'description');";
        
        jdbcTemplate.update(sql);  
        Course expected = new Course("name", "description");
        int expectedId = 5;
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = new Course("name", "description");  
        courseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = new Course("name", "description");
        courseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId).get());
        expected.setName("updated name");
        courseDao.update(expected);
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = new Course("name", "description");
        courseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId).get());
        courseDao.deleteById(expectedId);
        assertFalse(courseDao.getById(expectedId).isPresent());
    }
}