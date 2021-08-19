package com.foxminded.koren.university.dao;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.mappers.CourseMapper;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.util.comparator.Comparators;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcGroupDaoTest {
           
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @Autowired
    private JdbcGroupDao jdbcGroupDao;
    
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
        assertEquals(expected, jdbcGroupDao.getById(1));        
    }
    
    @Test
    void getAll_shouldWorkCorrectly() {
        Group group1 = new Group("group name1");
        group1.setId(1);
        Group group2 = new Group("group name2");
        group2.setId(2);
        Group group3 = new Group("group name3");
        group3.setId(3);
        List<Group> expected = List.of(group1, group2, group3);
        assertEquals(expected, jdbcGroupDao.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 4;
        Group expected = new Group("test!!!");    
        jdbcGroupDao.save(expected);
        assertEquals(expected, jdbcGroupDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Group expected = jdbcGroupDao.getById(expectedId);
        expected.setName("changed name");
        jdbcGroupDao.update(expected);
        assertEquals(expected, jdbcGroupDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group group = jdbcGroupDao.getById(expectedId);
        jdbcGroupDao.deleteById(group.getId());
        assertThrows(DAOException.class, () -> jdbcGroupDao.getById(group.getId()), "No such id in database");
    }
    
    @Test
    void addCourse_shouldWorkCorrectly() {
        Group group = new Group("group name1");
        group.setId(1);
        Course course = new Course("name3", "desc3");
        course.setId(3);
        String sql = 
                "SELECT c.id course_id, c.name course_name, c.description course_description\r\n"
              + "FROM group_course gc \r\n"
              + "    JOIN course c ON gc.course_id = c.id \r\n"
              + "WHERE gc.group_id = 1\r\n"
              + "AND gc.course_id = 3;";
        
        List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
        assertTrue(courses.isEmpty());

        jdbcGroupDao.addCourse(group, course);
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
                "SELECT c.id course_id, c.name course_name, c.description course_description\r\n"
              + "FROM group_course gc \r\n"
              + "    JOIN course c ON gc.course_id = c.id \r\n"
              + "WHERE gc.group_id = 1\r\n"
              + "AND gc.course_id = 2;";
        List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
        assertFalse(courses.isEmpty());
        jdbcGroupDao.removeCourse(group, course);
        courses = jdbcTemplate.query(sql, new CourseMapper());
        assertTrue(courses.isEmpty());
    }

    @Test
    void getGroupsByLectureIdShouldGetGroupsReliedToCurrentLecture() {
        String sql = "INSERT INTO lecture_group(lecture_id, group_id)\n" +
                "VALUES\n" +
                "    (1,2),\n" +
                "    (2,3),\n" +
                "    (4,2),\n" +
                "    (3,2),\n" +
                "    (3,3);";

        jdbcTemplate.execute(sql);

        List<Group> expected = List.of(new Group(3, "group name3"), new Group(2, "group name2"));

        expected = expected.stream().sorted((g1,g2) -> g1.getName().compareTo(g2.getName())).collect(toList());
        System.out.println(expected);
        assertEquals(expected, jdbcGroupDao.getGroupsByLectureId(3));
    }
}