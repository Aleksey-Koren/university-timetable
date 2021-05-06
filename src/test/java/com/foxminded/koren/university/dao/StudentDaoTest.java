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
import com.foxminded.koren.university.domain.entity.Group;
import com.foxminded.koren.university.domain.entity.Student;
import com.foxminded.koren.university.domain.entity.Year;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class StudentDaoTest {
    
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private StudentDao studentDao;
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
        Group group = new Group("group name1");
        group.setId(1);
        Student expected = new Student(group, "first name1", "last name1", Year.SECOND);
        expected.setId(expectedId);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    void getById_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 4;
        Group group = null;
        Student expected = new Student(group, "first name4", "last name4", Year.SECOND);
        expected.setId(expectedId);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 5;
        Group group = new Group("group name1");
        group.setId(1);
        Student expected = new Student(group, "test!!!", "test", Year.SIXTH);    
        studentDao.save(expected);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly_ifGroupIdIsNull() {        
        int expectedId = 5;
        Group group = null;
        Student expected = new Student(group, "test!!!", "test", Year.SIXTH);    
        studentDao.save(expected);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        Group group = new Group("group name2");
        group.setId(2);
        int expectedId = 1;
        Student expected = studentDao.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(group);
        expected.setYear(Year.FIRST);
        studentDao.update(expected);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 1;
        Student expected = studentDao.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(null);
        expected.setYear(Year.FIRST);
        studentDao.update(expected);
        assertEquals(expected, studentDao.getById(expectedId));
    }
    
    @Test
    
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Student student = studentDao.getById(expectedId);
        studentDao.deleteById(student.getId());
        assertThrows(DAOException.class, () -> studentDao.getById(student.getId()), "No such id in database");
    }
}