package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

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
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcCourseDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcCourseDao jdbcCourseDao;
    @Autowired
    private TestData testData;
    
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }

    @Test
    void getById_shouldWorkCorrectly() {
        Course expected = new Course("name4", "desc4");
        int expectedId = 4;
        expected.setId(expectedId);
        assertEquals(expected, jdbcCourseDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 5;
        Course expected = new Course("name", "description");  
        jdbcCourseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, jdbcCourseDao.getById(expectedId));
//        jdbcCourseDao.save(expected);
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = jdbcCourseDao.getById(expectedId);
        expected.setName("changed");
        expected.setDescrption("changed");
        jdbcCourseDao.update(expected);
        assertEquals(expected, jdbcCourseDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 3;
        Course course = jdbcCourseDao.getById(expectedId);
        jdbcCourseDao.deleteById(course.getId());
        assertThrows(DAOException.class, () -> jdbcCourseDao.getById(course.getId()), "No such id in database");
    }
    
    @Test
    void getAll_shouldWorkCorrectly() {
        Course course1 = new Course("name1", "desc1");
        course1.setId(1);
        Course course2 = new Course("name2", "desc2");
        course2.setId(2);
        Course course3 = new Course("name3", "desc3");
        course3.setId(3);
        Course course4 = new Course("name4", "desc4");
        course4.setId(4);
        List<Course> expected = List.of(course1, course2, course3, course4);
        assertEquals(expected, jdbcCourseDao.getAll());
    }
    
    @Test 
    void deleteById_shouldThrowException_whenConstraintReferencesPresent(){
        int expectedId = 1;
        Course course = jdbcCourseDao.getById(expectedId);
        assertThrows(DAOException.class, () -> jdbcCourseDao.deleteById(course.getId()));
    }
    
    @Test 
    void getByGroup_shouldWorkCorrectly() {
       Course course1 = new Course("name1", "desc1");
       course1.setId(1);
       Course course2 = new Course("name2", "desc2");
       course2.setId(2);
       Course course3 = new Course("name4", "desc4");
       course3.setId(4);
       Group group = new Group(" ");
       group.setId(2);
       assertEquals(List.of(course1, course2, course3), jdbcCourseDao.getByGroup(group));
    }
}