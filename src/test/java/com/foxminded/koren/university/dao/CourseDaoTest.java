package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.koren.university.domain.entity.Course;

@TestInstance(Lifecycle.PER_CLASS)
class CourseDaoTest {
    
    ClassPathXmlApplicationContext context;
    
    private TablesCreation tablesCreation;
    
    private JdbcTemplate jdbcTemplate;
    
    private CourseDao courseDao;
    
    @BeforeAll
    void contextInit() {
        context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        courseDao = context.getBean("courseDao", CourseDao.class);
    }
    
    @AfterAll
    void closeContext() {    
        context.close();
    }

    
    @Test
    void getById_shouldWorkCorrectly() throws IOException {
        
        tablesCreation.createTables();
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
}