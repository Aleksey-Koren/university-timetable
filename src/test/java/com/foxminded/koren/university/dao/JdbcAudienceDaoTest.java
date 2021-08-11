package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

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
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.entity.Audience;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcAudienceDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcAudienceDao jdbcAudienceDao;
    @Autowired
    private TestData testData;
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }
    
    @Test
    void getById_shouldWorkCorrectly() {  
        Audience expected = new Audience(4, 30);
        int expectedId = 1;
        expected.setId(expectedId);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
    }
    
    @Test
    void getAll_shouldWorkCorrectly() {
        Audience audience1 = new Audience(4, 30);
        audience1.setId(1);
        Audience audience2 = new Audience(34, 30);
        audience2.setId(2);
        Audience audience3 = new Audience(37, 150);
        audience3.setId(3);
        List<Audience> expected = List.of(audience1, audience2, audience3);
        assertEquals(expected, jdbcAudienceDao.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 4;
        Audience expected = new Audience(113, 30);  
        jdbcAudienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience expected = new Audience(4, 30);  
        expected.setId(expectedId);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
        expected.setNumber(35);
        jdbcAudienceDao.update(expected);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Audience audience = new Audience(4, 30);  
        audience.setId(expectedId);
        assertEquals(audience, jdbcAudienceDao.getById(expectedId));
        jdbcAudienceDao.deleteById(expectedId);
        assertThrows(DAOException.class, () -> jdbcAudienceDao.getById(audience.getId()), "No such id in database");
    }    
}