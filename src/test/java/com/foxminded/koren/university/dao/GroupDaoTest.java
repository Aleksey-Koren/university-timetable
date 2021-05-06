package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.foxminded.koren.university.dao.mappers.CourseMapper;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class GroupDaoTest {
           
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @Autowired
    private GroupDao groupDao;
    
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
        Group expected = new Group("group name1");
        expected.setId(expectedId);
        assertEquals(expected, groupDao.getById(1));        
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 3;
        Group expected = new Group("test!!!");    
        groupDao.save(expected);
        assertEquals(expected, groupDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Group expected = groupDao.getById(expectedId);
        expected.setName("changed name");
        groupDao.update(expected);
        assertEquals(expected, groupDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group group = groupDao.getById(expectedId);
        groupDao.deleteById(group.getId());
        assertThrows(DAOException.class, () -> groupDao.getById(group.getId()), "No such id in database");
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