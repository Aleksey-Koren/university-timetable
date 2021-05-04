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
    
    private JdbcTemplate jdbcTemplate;
    
    private TablesCreation tablesCreation;
    
    private GroupDao groupDao;
    
    private TestData testData;
    
    @BeforeAll
    void contextInit() {
        context = new AnnotationConfigApplicationContext(SpringConfigT.class);
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        tablesCreation = context.getBean("tablesCreation", TablesCreation.class);
        groupDao = context.getBean("groupDao", GroupDao.class);
        testData = context.getBean("testData", TestData.class); 
        
    }
    
    @AfterAll
    void closeContext() {    
        context.close();
    }
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }
    
    @Test
    void getById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group expected = new Group("group name1");
        expected.setId(expectedId);
        assertEquals(expected, groupDao.getById(1).get());        
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 3;
        Group expected = new Group("test!!!");    
        groupDao.save(expected);
        assertEquals(expected, groupDao.getById(expectedId).get());
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Group expected = groupDao.getById(expectedId).get();
        expected.setName("changed name");
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(expectedId).get());
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        assertTrue(groupDao.getById(expectedId).isPresent());
        groupDao.deleteById(expectedId);
        assertFalse(groupDao.getById(expectedId).isPresent());
    }
    
    @Test
    void addCourse_shouldWorkCorrectly() {
        Group group = new Group("group name1");
        group.setId(1);
        Course course = new Course("name3", "desc3");
        course.setId(3);
        String sql = 
                "SELECT c.id, c.name, c.description\r\n"
              + "FROM group_course gc \r\n"
              + "    JOIN course c ON gc.course_id = c.id \r\n"
              + "WHERE gc.group_id = 1\r\n"
              + "AND gc.course_id = 3;";
        
        List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
        assertTrue(courses.isEmpty());

        groupDao.addCourse(group, course);
        courses = jdbcTemplate.query(sql, new CourseMapper());
        assertFalse(courses.isEmpty());
        assertEquals(course, courses.get(0));
    }
    
    @Test
    void removeCourse_shouldWorkCorrectly() {
        Group group = new Group("group name1");
        group.setId(1);
        Course course = new Course("name3", "desc3");
        course.setId(2);
        String sql = 
                "SELECT c.id, c.name, c.description\r\n"
              + "FROM group_course gc \r\n"
              + "    JOIN course c ON gc.course_id = c.id \r\n"
              + "WHERE gc.group_id = 1\r\n"
              + "AND gc.course_id = 2;";
        List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
        assertFalse(courses.isEmpty());
        groupDao.removeCourse(group, course);
        courses = jdbcTemplate.query(sql, new CourseMapper());
        assertTrue(courses.isEmpty());
    }
}