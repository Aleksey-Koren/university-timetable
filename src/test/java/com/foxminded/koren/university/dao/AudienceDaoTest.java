package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.domain.entity.Audience;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class AudienceDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AudienceDao audienceDao;
    
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
        assertEquals(expected, audienceDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(113, 30);  
        audienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(113, 30);  
        audienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceDao.getById(expectedId));
        expected.setNumber(35);
        audienceDao.update(expected);
        assertEquals(expected, audienceDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience audience = new Audience(113, 30);  
        audienceDao.save(audience);
        audience.setId(expectedId);
        assertEquals(audience, audienceDao.getById(expectedId));
        audienceDao.deleteById(expectedId);
        assertThrows(DAOException.class, () -> audienceDao.getById(audience.getId()), "No such id in database");
    }    
}