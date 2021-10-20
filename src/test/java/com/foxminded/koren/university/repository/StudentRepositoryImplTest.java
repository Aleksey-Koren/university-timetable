package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.StudentRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
class StudentRepositoryImplTest {


    @Autowired
    @Qualifier("studentRepositoryImpl")
    private StudentRepository studentRepository;
    @Autowired
    private JpaTestData testData;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        testData.createTables();
        testData.loadTestData();
    }

    @Test
    void getById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group group = new Group("group name1", Year.FIRST);
        group.setId(1);
        Student expected = new Student(group, "first name1", "last name1");
        expected.setId(expectedId);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void getById_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 4;
        Group group = null;
        Student expected = new Student(group, "first name4", "last name4");
        expected.setId(expectedId);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void getAll_shouldWorkCorrectly() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM student").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO student (id, group_id, first_name, last_name)\n"
                        + "VALUES\n"
                        + "(1, 1, 'first name1', 'last name1'),\r\n"
                        + "(2, 2, 'first name2', 'last name2');")
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        Group group1 = new Group("group name1", Year.FIRST);
        group1.setId(1);
        Group group2 = new Group("group name2", Year.SECOND);
        group2.setId(2);
        Student student1 = new Student(group1, "first name1", "last name1");
        student1.setId(1);
        Student student2 = new Student(group2, "first name2", "last name2");
        student2.setId(2);
        List<Student> expected = List.of(student1, student2);
        assertEquals(expected, studentRepository.getAll());
    }

    @Test
    void save_shouldWorkCorrectly() {
        int expectedId = 5;
        Group group = new Group("group name1", Year.FIRST);
        group.setId(1);
        Student expected = new Student(group, "test!!!", "test");
        studentRepository.save(expected);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void save_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 5;
        Group group = null;
        Student expected = new Student(group, "test!!!", "test");
        studentRepository.save(expected);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void update_shouldWorkCorrectly() {
        Group group = new Group("group name2", Year.SECOND);
        group.setId(2);
        int expectedId = 1;
        Student expected = studentRepository.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(group);
        studentRepository.update(expected);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void update_shouldWorkCorrectly_ifGroupIsNull() {
        int expectedId = 1;
        Student expected = studentRepository.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(null);
        studentRepository.update(expected);
        assertEquals(expected, studentRepository.getById(expectedId));
    }

    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Student student = studentRepository.getById(expectedId);
        studentRepository.deleteById(student.getId());
        assertThrows(RepositoryException.class, () -> studentRepository.getById(student.getId()), "No such id in database");
    }

    @Test
    void getByGroupId_shouldReturnAllStudentsAttachedToArgumentGroup() {
        Student student1 = new Student(2, new Group(2, "group name2", Year.SECOND), "first name2", "last name2");
        Student student2 = new Student(3, new Group(2, "group name2", Year.SECOND), "first name3", "last name3");
        List<Student> expected = List.of(student1, student2);
        assertEquals(expected, studentRepository.getByGroupId(2));
    }

    @Test
    void getALLWithoutGroup_shouldReturnAllStudentsWhereGroupIdIsNull() {
        List<Student> expected = List.of(studentRepository.getById(4));
        assertEquals(expected, studentRepository.getAllWithoutGroup());
    }

    @Test
    void addStudentToGroup_shouldSetArgsGroupIdToArgsStudent() {
        int studentId = 4;
        int groupId = 2;
        assertNull(studentRepository.getById(studentId).getGroup());
        studentRepository.addStudentToGroup(studentId, groupId);
        assertNotNull(studentRepository.getById(studentId).getGroup());
        assertTrue(studentRepository.getById(studentId).getGroup().getId() == groupId);
    }

    @Test
    void removeStudentFromGroup_shouldSetGroupIdAsNullInArgumentStudent() {
        int testStudentId = 3;
        assertNotNull(studentRepository.getById(testStudentId).getGroup());
        studentRepository.removeStudentFromGroup(testStudentId);
        assertNull(studentRepository.getById(testStudentId).getGroup());
    }
}