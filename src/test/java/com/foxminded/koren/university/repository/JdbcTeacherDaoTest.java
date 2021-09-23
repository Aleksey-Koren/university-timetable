package com.foxminded.koren.university.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import com.foxminded.koren.university.repository.jdbcDao.JdbcTeacherDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.repository.test_data.TestData;
import com.foxminded.koren.university.entity.Teacher;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
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
    void getAll_shouldWorkCorrectly() {
        Teacher teacher1 = new Teacher("first name1", "last name1");
        teacher1.setId(1);
        Teacher teacher2 = new Teacher("first name2", "last name2");
        teacher2.setId(2);
        List<Teacher> expected = List.of(teacher1, teacher2);
        assertEquals(expected, jdbcTeacherDao.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 3;
        assertThrows(RepositoryException.class, () -> jdbcTeacherDao.getById(expectedId), "No such id in database");
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
        assertThrows(RepositoryException.class, () -> jdbcTeacherDao.getById(teacher.getId()), "No such id in database");

    }
}