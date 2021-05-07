package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.domain.entity.Teacher;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcTeacherDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcTeacherDao jdbcTeacherDao;
    @Autowired
    private TestData testData;

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
        assertEquals(expected, jdbcTeacherDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 3;
        assertThrows(DAOException.class, () -> jdbcTeacherDao.getById(expectedId), "No such id in database");
        Teacher expected = new Teacher("first name", "last name");  
        jdbcTeacherDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, jdbcTeacherDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher expected = jdbcTeacherDao.getById(expectedId);
        expected.setFirstName("changed");
        expected.setLastName("changed");
        jdbcTeacherDao.update(expected);
        assertEquals(expected, jdbcTeacherDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher teacher = jdbcTeacherDao.getById(expectedId);
        jdbcTeacherDao.deleteById(teacher.getId());
        assertThrows(DAOException.class, () -> jdbcTeacherDao.getById(teacher.getId()), "No such id in database");

    }
}