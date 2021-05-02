package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

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
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@TestInstance(Lifecycle.PER_CLASS)
class GroupDaoTest {
    
    
    AnnotationConfigApplicationContext context;
    
    private TablesCreation tablesCreation;
    
    private JdbcTemplate jdbcTemplate;
    
    private GroupDao groupDao;
    
    @BeforeAll
    void contextInit() {
        context = new AnnotationConfigApplicationContext(SpringConfigT.class);
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        groupDao = context.getBean("groupDao", GroupDao.class);
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
        prepareTestData();
        
        Course course1 = new Course("name1", "desc1");
        course1.setId(1);
        Course course2 = new Course("name2", "desc2");
        course2.setId(2);
        
        int expectedId = 1;
        Group expected = new Group("group name1", List.of(course1, course2));
        expected.setId(expectedId);
                
        assertEquals(expected, groupDao.getById(1).get());        
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        prepareTestData();
        
        int expectedId = 3;
        Group expected = new Group("test");
        Course course1 = new Course("name1", "desc1");
        course1.setId(1);
        Course course2 = new Course("name2", "desc2");
        course2.setId(2);
        expected.setCourses(List.of(course1, course2));
        
        groupDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, groupDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        prepareTestData();
        int expectedId = 1;
        Group expected = groupDao.getById(expectedId).get();
        expected.setName("changed name");
        expected.getCourses().remove(0);
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        prepareTestData();
        int expectedId = 1;
        assertTrue(groupDao.getById(expectedId).isPresent());
        groupDao.deleteById(expectedId);
        assertFalse(groupDao.getById(expectedId).isPresent());
    }
    
    private void prepareTestData() {
        String insertCourses = 
                "INSERT INTO course (name, description)\n"
              + "VALUES\n"
              + "('name1', 'desc1'),\n"
              + "('name2', 'desc2'),\n"
              + "('name3', 'desc3'),\n"
              + "('name4', 'desc4');";
        jdbcTemplate.update(insertCourses);
   
        String insertGroups = 
                "INSERT INTO group_table (name)\n"
              + "VALUES\n"
              + "('group name1'),\n"
              + "('group name2');";    
        jdbcTemplate.update(insertGroups);
   
        String addCoursesToGroup = "INSERT INTO group_course (group_id, course_id)\r\n"
                            + "VALUES\n"
                            + "(1, 1),\n"
                            + "(1, 2),\n"
                            + "(2, 1),\n"
                            + "(2, 2),\n"
                            + "(2, 4)";      
        jdbcTemplate.update(addCoursesToGroup);
    }
}