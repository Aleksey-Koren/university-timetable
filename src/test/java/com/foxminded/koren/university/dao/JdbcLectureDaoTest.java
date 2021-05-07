package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.dao.test_data.TestData;
import com.foxminded.koren.university.domain.entity.Audience;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Lecture;
import com.foxminded.koren.university.domain.entity.Teacher;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcLectureDaoTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @Autowired
    private JdbcLectureDao jdbcLectureDao;
    
    @Autowired
    private TestData testData;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }
    
    @Test
    void getById_shouldGetById() {
        int expectedId = 1;
        Lecture expected = prepareExpected(expectedId); 
        assertEquals(expected, jdbcLectureDao.getById(expectedId));
    }
    
    @Test
    void getById_shouldGetById_whenTeacherOrAudienceIsNull() {
        int expectedId = 1;
        Lecture expected = prepareExpected(expectedId);
        jdbcTemplate.execute("UPDATE lecture\r\n"
                           + "SET teacher_id = NULL;");
        
        expected.setTeacher(null);
        assertEquals(expected, jdbcLectureDao.getById(expectedId));
        
        jdbcTemplate.execute("UPDATE lecture\r\n"
                           + "SET audience_id = NULL;");
        
        expected.setAudience(null);
        assertEquals(expected, jdbcLectureDao.getById(expectedId));
        
    }
    
    private Lecture prepareExpected(int expectedId) {
        Audience audience = new Audience(4, 30);
        audience.setId(1);
        Teacher teacher = new Teacher("first name1", "last name1");
        teacher.setId(1);
        Course course = new Course("name1", "desc1");
        course.setId(1);
        Lecture expected = new Lecture(audience,
                                       teacher,
                                       course,
                                       LocalDateTime.of(2021, 5, 2, 16, 0),
                                       LocalDateTime.of(2021, 5, 2, 17, 0));
        expected.setId(expectedId);
        return expected;
    }
}