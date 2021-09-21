package com.foxminded.koren.university.dao;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.entity.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class AudienceDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    @Qualifier("jpaAudienceDao")
    private AudienceDao jdbcAudienceDao;
    @Autowired
    private TestData testData;

    @Test
    void getById_shouldWorkCorrectly() throws IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
        Audience expected = new Audience(1, 4, 30);
        assertEquals(expected, jdbcAudienceDao.getById(expected.getId()));
    }
    
    @Test
    void getAll_shouldWorkCorrectly() throws IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
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
    void save_shouldWorkCorrectly() throws IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
        int expectedId = 4;
        Audience expected = new Audience(113, 30);  
        jdbcAudienceDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() throws IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
        int expectedId = 1;
        Audience expected = new Audience(4, 30);  
        expected.setId(expectedId);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
        expected.setNumber(35);
        jdbcAudienceDao.update(expected);
        assertEquals(expected, jdbcAudienceDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() throws IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
        int expectedId = 1;
        Audience audience = new Audience(4, 30);  
        audience.setId(expectedId);
        assertEquals(audience, jdbcAudienceDao.getById(expectedId));
        jdbcAudienceDao.deleteById(expectedId);
        assertThrows(DAOException.class, () -> jdbcAudienceDao.getById(audience.getId()),
                String.format
                        ("Unable to delete audience with id = %s, cause: there is no audience with such id in database",
                                expectedId));
    }

    @Test
    void getAll_shouldSortAudiencesByName() throws IOException {
        tablesCreation.createTables();
        Audience audience1 = new Audience(5, 30);
        jdbcAudienceDao.save(audience1);
        Audience audience2 = new Audience(9, 30);
        jdbcAudienceDao.save(audience2);
        Audience audience3 = new Audience(6, 150);
        jdbcAudienceDao.save(audience3);
        List<Audience> audiences = List.of(audience1, audience2, audience3);
        assertNotEquals(audiences, jdbcAudienceDao.getAll());
        audiences = audiences.stream().sorted(comparingInt(Audience::getNumber)).collect(Collectors.toList());
        assertEquals(audiences, jdbcAudienceDao.getAll());
    }
}