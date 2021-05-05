package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class CourseDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseDao courseDao;
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
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 5;
        Course expected = new Course("name", "description");  
        courseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = courseDao.getById(expectedId).get();
        expected.setName("changed");
        expected.setDescrption("changed");
        courseDao.update(expected);
        assertEquals(expected, courseDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Course toDelete = courseDao.getById(expectedId).get();
        courseDao.deleteById(toDelete.getId());
        assertFalse(courseDao.getById(expectedId).isPresent());  
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
       assertEquals(List.of(course1, course2, course3), courseDao.getByGroup(group));
    }
}