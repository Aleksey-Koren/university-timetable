package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.CourseRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.repository.test_data.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class CourseRepositoryImplTest {

    @Autowired
    @Qualifier("courseRepositoryImpl")
    private CourseRepository courseDao;
    @Autowired
    private JpaTestData testData;


    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        testData.createTables();
        testData.loadTestData();
    }

    @Test
    void getById_shouldWorkCorrectly() {
        Course expected = new Course("name4", "desc4");
        int expectedId = 4;
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId));
    }

    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 5;
        Course expected = new Course("name", "description");
        courseDao.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, courseDao.getById(expectedId));
    }

    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Course expected = courseDao.getById(expectedId);
        expected.setName("changed");
        expected.setDescription("changed");
        courseDao.update(expected);
        assertEquals(expected, courseDao.getById(expectedId));
    }

    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 3;
        Course course = courseDao.getById(expectedId);
        courseDao.deleteById(course.getId());
        RepositoryException exception = assertThrows(RepositoryException.class, () -> courseDao.getById(course.getId()));
        String expectedMessage = String
                .format("Unable to delete course with id = %s, cause: there is no course with such id in database",
                        expectedId);
        assertEquals(expectedMessage, exception.getMessage());
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
        assertEquals(expected, courseDao.getAll());
    }
}