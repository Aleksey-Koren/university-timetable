package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.TeacherRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
class TeacherRepositoryImplTest {
    

    @Autowired
    @Qualifier("teacherRepositoryImpl")
    private TeacherRepository teacherRepository;
    @Autowired
    private JpaTestData testData;

    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        testData.createTables();
        testData.loadTestData();
    }
    
    @Test
    void getById_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher expected = new Teacher("first name1", "last name1");
        expected.setId(expectedId);
        assertEquals(expected, teacherRepository.getById(expectedId));
    }
    
    @Test
    void getAll_shouldWorkCorrectly() {
        Teacher teacher1 = new Teacher("first name1", "last name1");
        teacher1.setId(1);
        Teacher teacher2 = new Teacher("first name2", "last name2");
        teacher2.setId(2);
        List<Teacher> expected = List.of(teacher1, teacher2);
        assertEquals(expected, teacherRepository.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 3;
        assertThrows(RepositoryException.class, () -> teacherRepository.getById(expectedId), "No such id in database");
        Teacher expected = new Teacher("first name", "last name");  
        teacherRepository.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, teacherRepository.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher expected = teacherRepository.getById(expectedId);
        expected.setFirstName("changed");
        expected.setLastName("changed");
        teacherRepository.update(expected);
        assertEquals(expected, teacherRepository.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Teacher teacher = teacherRepository.getById(expectedId);
        teacherRepository.deleteById(teacher.getId());
        RepositoryException exception = assertThrows(RepositoryException.class, () -> teacherRepository.getById(expectedId));
        assertEquals(String
                .format("Unable to get teacher with id = %s, cause: there is no teacher with such id in database", expectedId),
                exception.getMessage());
    }
}