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

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.domain.entity.Teacher;

@TestInstance(Lifecycle.PER_CLASS)
class TeacherDaoTest {
    
    AnnotationConfigApplicationContext context;
    
    private JdbcTemplate jdbcTemplate;
    
    private TablesCreation tablesCreation;
    
    private TeacherDao teacherDao;
    
    private TestData testData;
    
    @BeforeAll
    void contextInit() {
        context = new AnnotationConfigApplicationContext(SpringConfigT.class);
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        teacherDao = context.getBean("teacherDao", TeacherDao.class);
        testData = context.getBean("testData", TestData.class); 
    }
    
    @AfterAll
    void closeContext() {    
        context.close();
    }
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }
    
    @Test
    void getById_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher expected = new Teacher("first name1", "last name1");
        expected.setId(expectedId);
        assertEquals(expected, teacherDao.getById(expectedId).get());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 3;
        assertFalse(teacherDao.getById(expectedId).isPresent());
        Teacher expected = new Teacher("first name", "last name");  
        teacherDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, teacherDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher expected = teacherDao.getById(expectedId).get();
        expected.setFirstName("changed");
        expected.setLastName("changed");
        teacherDao.update(expected);
        assertEquals(expected, teacherDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher toDelete = teacherDao.getById(expectedId).get();
        teacherDao.deleteById(toDelete.getId());
        assertFalse(teacherDao.getById(expectedId).isPresent());  
    }
}