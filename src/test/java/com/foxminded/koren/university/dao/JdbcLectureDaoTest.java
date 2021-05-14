package com.foxminded.koren.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
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
    
    @Test
    void getAll_shouldGetById_whenTeacherOrAudienceIsNull() {
        jdbcTemplate.execute("DELETE FROM lecture;");
        jdbcTemplate.execute("INSERT INTO lecture \r\n"
                           + "(id, course_id, teacher_id, audience_id, start_time , end_time)\r\n"
                           + "VALUES\r\n"
                           + "(1 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),\r\n"
                           + "(2 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00');");
        
        Lecture lecture1 = prepareExpected(1);
        Lecture lecture2 = prepareExpected(2);
        List<Lecture> expected = List.of(lecture1, lecture2);
        assertEquals(expected, jdbcLectureDao.getAll());
    }
    
    @Test
    void save_shouldSaveAndGetGeneratedKey() {
        int presentId = 1;
        int savedId = 10;
        Lecture lecture = jdbcLectureDao.getById(presentId);
        assertEquals(presentId, lecture.getId());
        assertThrows(DAOException.class, () -> jdbcLectureDao.getById(savedId), "No such id in database");
        jdbcLectureDao.save(lecture);
        assertEquals(savedId, lecture.getId());
        assertEquals(lecture, jdbcLectureDao.getById(savedId));
    }
    
    @Test
    void save_shouldSave_whenTeacherOrAudienceIsNull() {
        int presentId = 1;
        int savedId = 10;
        Lecture lecture = jdbcLectureDao.getById(presentId); 
        assertEquals(presentId, lecture.getId());
        assertThrows(DAOException.class, () -> jdbcLectureDao.getById(savedId),
                "No such id in database");
        lecture.setTeacher(null);
        lecture.setAudience(null);
        jdbcLectureDao.save(lecture);
        assertEquals(savedId, lecture.getId());
        assertEquals(lecture, jdbcLectureDao.getById(savedId));
    }
    
    @Test
    void update_shouldUpdateCorrectly() {
        int lectureId = 1;
        int presentId = 1;
        int updatedId = 2;
        Lecture lecture = jdbcLectureDao.getById(lectureId);
        assertEquals(presentId, lecture.getCourse().getId());
        assertEquals(presentId, lecture.getTeacher().getId());
        assertEquals(presentId, lecture.getAudience().getId());
        lecture.getCourse().setId(updatedId);
        lecture.getTeacher().setId(updatedId);
        lecture.getAudience().setId(updatedId);
        jdbcLectureDao.update(lecture);
        assertEquals(updatedId, jdbcLectureDao.getById(lectureId).getCourse().getId());
        assertEquals(updatedId, jdbcLectureDao.getById(lectureId).getTeacher().getId());
        assertEquals(updatedId, jdbcLectureDao.getById(lectureId).getAudience().getId());
    }
    
    @Test
    void update_shouldUpdateCorrectly_whenTeacherOrAudienceIsNull() {
        int lectureId = 1;
        int presentId = 1;
        int updatedId = 2;
        Lecture lecture = jdbcLectureDao.getById(lectureId);
        assertEquals(presentId, lecture.getCourse().getId());
        assertEquals(presentId, lecture.getTeacher().getId());
        assertEquals(presentId, lecture.getAudience().getId());
        lecture.getCourse().setId(updatedId);
        lecture.setTeacher(null);
        lecture.setAudience(null);
        jdbcLectureDao.update(lecture);
        assertEquals(updatedId, jdbcLectureDao.getById(lectureId).getCourse().getId());
        assertNull(jdbcLectureDao.getById(lectureId).getTeacher());
        assertNull(jdbcLectureDao.getById(lectureId).getAudience());
    }
    
    @Test
    void deleteById_shouldDeleteWhenIdProvided() {
        int lectureId = 1;
        Lecture lecture = jdbcLectureDao.getById(lectureId);
        jdbcLectureDao.deleteById(lecture.getId());
        assertThrows(DAOException.class, () -> jdbcLectureDao.getById(lecture.getId()),
                "No such id in database");
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