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
import com.foxminded.koren.university.domain.entity.Audience;

@TestInstance(Lifecycle.PER_CLASS)
class AudienceDaoTest {
    
    AnnotationConfigApplicationContext context;
    
    private TablesCreation tablesCreation;
    
    private JdbcTemplate jdbcTemplate;
    
    private AudienceDao audienceDao;
    
    @BeforeAll
    void contextInit() {
        context = new AnnotationConfigApplicationContext(SpringConfigT.class);
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        audienceDao = context.getBean("audienceDao", AudienceDao.class);
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
        String sql = "INSERT INTO audience (id, room_number, capacity)\r\n"
                   + "VALUES (5, 113, 30);";
        
        jdbcTemplate.update(sql);  
        Audience expected = new Audience(113, 30);
        int expectedId = 5;
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId).get());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(113, 30);  
        audienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(113, 30);  
        audienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId).get());
        expected.setNumber(35);
        audienceDao.update(expected);
        assertEquals(expected, audienceDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(113, 30);  
        audienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId).get());
        audienceDao.deleteById(expectedId);
        assertFalse(audienceDao.getById(expectedId).isPresent());
    }    
}