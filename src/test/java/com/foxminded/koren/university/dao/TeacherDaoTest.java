package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.domain.entity.Teacher;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class TeacherDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private TeacherDao teacherDao;
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