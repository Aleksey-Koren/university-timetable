package com.foxminded.koren.university.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.koren.university.repository.interfaces.LectureRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class LectureRepositoryImplTest {

    @Autowired
    @Qualifier("lectureRepositoryImpl")
    private LectureRepository lectureRepository;

    @Autowired
    private JpaTestData testData;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        testData.createTables();
        testData.loadTestData();
    }

    @Test
    void getById_shouldGetById() {
        int expectedId = 1;
        TimetableEvent expected = prepareExpected(expectedId);
        Lecture lecture = lectureRepository.getById(expectedId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + lecture.getGroups().get(0).getLectures());
        assertEquals(expectedId, lecture.getId());
    }
    
    @Test
    void getById_shouldGetById_whenTeacherOrAudienceIsNull() {
        int expectedId = 1;
        TimetableEvent expected = prepareExpected(expectedId);
        jdbcTemplate.execute("UPDATE lecture\r\n"
                           + "SET teacher_id = NULL;");
        
        expected.setTeacher(null);
        assertEquals(expected, lectureRepository.getById(expectedId));
        
        jdbcTemplate.execute("UPDATE lecture\r\n"
                           + "SET audience_id = NULL;");
        
        expected.setAudience(null);
        assertEquals(expected, lectureRepository.getById(expectedId));
    }
    
    @Test
    void getAll_shouldGetAll_whenTeacherOrAudienceIsNull() {
        jdbcTemplate.execute("DELETE FROM lecture;");
        jdbcTemplate.execute("INSERT INTO lecture \r\n"
                           + "(id, course_id, teacher_id, audience_id, start_time , end_time)\r\n"
                           + "VALUES\r\n"
                           + "(1 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),\r\n"
                           + "(2 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00');");
        
        Lecture lecture1 = prepareExpected(1);
        Lecture lecture2 = prepareExpected(2);
        List<TimetableEvent> expected = List.of(lecture1, lecture2);
        assertEquals(expected, lectureRepository.getAll());
    }
    
    @Test
    void save_shouldSaveAndGetGeneratedKey() {
        int presentId = 1;
        int savedId = 10;
        Lecture lecture = lectureRepository.getById(presentId);
        assertEquals(presentId, lecture.getId());
        assertThrows(RepositoryException.class, () -> lectureRepository.getById(savedId), "No such id in database");
        lectureRepository.save(lecture);
        assertEquals(savedId, lecture.getId());
        assertEquals(lecture, lectureRepository.getById(savedId));
    }
    
    @Test
    void save_shouldSave_whenTeacherOrAudienceIsNull() {
        int presentId = 1;
        int savedId = 10;
        Lecture lecture = lectureRepository.getById(presentId);
        assertEquals(presentId, lecture.getId());
        assertThrows(RepositoryException.class, () -> lectureRepository.getById(savedId),
                "No such id in database");
        lecture.setTeacher(null);
        lecture.setAudience(null);
        lectureRepository.save(lecture);
        assertEquals(savedId, lecture.getId());
        assertEquals(lecture, lectureRepository.getById(savedId));
    }
    
    @Test
    void update_shouldUpdateCorrectly() {
        int lectureId = 1;
        int presentId = 1;
        int updatedId = 2;
        Lecture lecture = lectureRepository.getById(lectureId);
        assertEquals(presentId, lecture.getCourse().getId());
        assertEquals(presentId, lecture.getTeacher().getId());
        assertEquals(presentId, lecture.getAudience().getId());
        lecture.getCourse().setId(updatedId);
        lecture.getTeacher().setId(updatedId);
        lecture.getAudience().setId(updatedId);
        lectureRepository.update(lecture);
        assertEquals(updatedId, lectureRepository.getById(lectureId).getCourse().getId());
        assertEquals(updatedId, lectureRepository.getById(lectureId).getTeacher().getId());
        assertEquals(updatedId, lectureRepository.getById(lectureId).getAudience().getId());
    }
    
    @Test
    void update_shouldUpdateCorrectly_whenTeacherOrAudienceIsNull() {
        int lectureId = 1;
        int presentId = 1;
        int updatedId = 2;
        Lecture lecture = lectureRepository.getById(lectureId);
        assertEquals(presentId, lecture.getCourse().getId());
        assertEquals(presentId, lecture.getTeacher().getId());
        assertEquals(presentId, lecture.getAudience().getId());
        lecture.getCourse().setId(updatedId);
        lecture.setTeacher(null);
        lecture.setAudience(null);
        lectureRepository.update(lecture);
        assertEquals(updatedId, lectureRepository.getById(lectureId).getCourse().getId());
        assertNull(lectureRepository.getById(lectureId).getTeacher());
        assertNull(lectureRepository.getById(lectureId).getAudience());
    }
    
    @Test
    void deleteById_shouldDeleteWhenIdProvided() {
        int lectureId = 1;
        TimetableEvent lecture = lectureRepository.getById(lectureId);
        lectureRepository.deleteById(lecture.getId());
        assertThrows(RepositoryException.class, () -> lectureRepository.getById(lecture.getId()),
                "No such id in database");
    }
    
    @Test
    void getTeacherLecturesByTimePeriod_shouldWorkCorrectly() {      
        reinsertLectures();
        
        List<Lecture> expected = new ArrayList<>();
        int expectedLectureId1 = 4;
        int expectedLectureId2 = 6;
        expected.add(lectureRepository.getById(expectedLectureId1));
        expected.add(lectureRepository.getById(expectedLectureId2));
        
        int teacherId = 2;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        
        LocalDate start = LocalDate.of(2021, 6, 3);
        LocalDate finish = LocalDate.of(2021, 6, 5);
        
        assertEquals(expected, lectureRepository.getTeacherLecturesByTimePeriod(teacher, start, finish));
    }
    
    @Test
    void getStudentLecturesByTimePeriod_shouldWorkCorrectly() {
        reinsertLectures();
        jdbcTemplate.execute(
                  "INSERT INTO lecture_group\r\n"
                + "(group_id, lecture_id)\r\n"
                          
                + "VALUES\r\n"
                + "(2, 2),\r\n"
                + "(2, 4),\r\n"
                + "(2, 6);");
        
        List<Lecture> expected = new ArrayList<>();
        int expectedLectureId1 = 2;
        int expectedLectureId2 = 4;
        int expectedLectureId3 = 6;
        expected.add(lectureRepository.getById(expectedLectureId1));
        expected.add(lectureRepository.getById(expectedLectureId2));
        expected.add(lectureRepository.getById(expectedLectureId3));
        
        
        int testStudentId = 2;
        Student testStudent = new Student();
        testStudent.setId(testStudentId);
        
        LocalDate start = LocalDate.of(2021, 6, 2);
        LocalDate finish = LocalDate.of(2021, 6, 5);
        
        assertEquals(expected, lectureRepository.getStudentLecturesByTimePeriod(testStudent, start, finish));
    }
    
    private Lecture prepareExpected(int expectedId) {
        Audience audience = new Audience(4, 30);
        audience.setId(1);
        Teacher teacher = new Teacher("first name1", "last name1");
        teacher.setId(1);
        Course course = new Course("name1", "desc1");
        course.setId(1);

        Lecture expected = new Lecture.Builder()
                .id(expectedId)
                .audience(audience)
                .teacher(teacher)
                .course(course)
                .startTime(LocalDateTime.of(2021, 5, 2, 16, 0))
                .endTime(LocalDateTime.of(2021, 5, 2, 17, 0))
                .build();

        return expected;
    }
    
    private void reinsertLectures() {
        jdbcTemplate.execute("DELETE FROM lecture;");

        jdbcTemplate.execute(
                  "INSERT INTO lecture \r\n"
                + "(id, course_id, teacher_id, audience_id, start_time , end_time)\r\n"
                + "VALUES\r\n"
                + "(1 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),\r\n"
                + "(2 , 2, 2, 2, '2021-06-02 16:00:00', '2021-06-02 17:00:00'),\r\n"
                + "(3 , 1, 1, 1, '2021-06-02 18:00:00', '2021-06-02 19:00:00'),\r\n"
                + "(4 , 2, 2, 3, '2021-06-03 19:00:00', '2021-06-03 20:00:00'),\r\n"
                + "(5 , 1, 1, 2, '2021-06-02 21:00:00', '2021-06-02 22:00:00'),\r\n"
                + "(6 , 2, 2, 1, '2021-06-04 07:00:00', '2021-06-04 08:00:00'),\r\n"
                + "(7 , 1, 2, 2, '2021-06-06 23:59:59', '2021-06-07 01:00:00'),\r\n"
                + "(8 , 1, NULL, NULL, '2021-06-02 18:00:00', '2021-06-02 19:00:00'),\r\n"
                + "(9 , 1, NULL, 2, '2021-06-02 18:00:00', '2021-06-02 19:00:00');");
    }
}