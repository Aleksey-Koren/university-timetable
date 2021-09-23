package com.foxminded.koren.university.repository;

import java.io.IOException;
import java.util.List;

import com.foxminded.koren.university.repository.jdbcDao.JdbcStudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.repository.test_data.TestData;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
class JdbcStudentDaoTest {

    @Autowired
    private TablesCreation tablesCreation;
    @Autowired
    private JdbcStudentDao jdbcStudentDao;
    @Autowired
    private TestData testData;
    @Autowired
    private JdbcTemplate JdbcTemplate;
        
    @BeforeEach
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
        testData.prepareTestData();
    }
    
    @Test
    void getById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group group = new Group("group name1", Year.FIRST);
        group.setId(1);
        Student expected = new Student(group, "first name1", "last name1");
        expected.setId(expectedId);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void getById_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 4;
        Group group = null;
        Student expected = new Student(group, "first name4", "last name4");
        expected.setId(expectedId);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void getAll_shouldWorkCorrectly_ifGroupIdIsNull() {
        JdbcTemplate.execute("DELETE FROM student");
        JdbcTemplate.execute("INSERT INTO student (id, group_id, first_name, last_name)\n"
                           + "VALUES\n"
                           + "(1, 1, 'first name1', 'last name1'),\r\n"
                           + "(2, 2, 'first name2', 'last name2');");
        Group group1 = new Group("group name1", Year.FIRST);
        group1.setId(1);
        Group group2 = new Group("group name2", Year.SECOND);
        group2.setId(2);
        Student student1 = new Student(group1, "first name1", "last name1");
        student1.setId(1);
        Student student2 = new Student(group2, "first name2", "last name2");
        student2.setId(2);
        List<Student> expected = List.of(student1, student2);
        assertEquals(expected, jdbcStudentDao.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 5;
        Group group = new Group("group name1", Year.FIRST);
        group.setId(1);
        Student expected = new Student(group, "test!!!", "test");
        jdbcStudentDao.save(expected);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void save_shouldWorkCorrectly_ifGroupIdIsNull() {        
        int expectedId = 5;
        Group group = null;
        Student expected = new Student(group, "test!!!", "test");
        jdbcStudentDao.save(expected);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        Group group = new Group("group name2", Year.SECOND);
        group.setId(2);
        int expectedId = 1;
        Student expected = jdbcStudentDao.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(group);
        jdbcStudentDao.update(expected);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly_ifGroupIdIsNull() {
        int expectedId = 1;
        Student expected = jdbcStudentDao.getById(expectedId);
        expected.setFirstName("changed name");
        expected.setLastName("changed name");
        expected.setGroup(null);
        jdbcStudentDao.update(expected);
        assertEquals(expected, jdbcStudentDao.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Student student = jdbcStudentDao.getById(expectedId);
        jdbcStudentDao.deleteById(student.getId());
        assertThrows(RepositoryException.class, () -> jdbcStudentDao.getById(student.getId()), "No such id in database");
    }

    @Test
    void getByGroupId_shouldReturnAllStudentsAttachedToArgumentGroup() {
        Student student1 = new Student(2, new Group(2, "group name2", Year.SECOND), "first name2", "last name2");
        Student student2 = new Student(3, new Group(2, "group name2", Year.SECOND), "first name3", "last name3");
        List<Student> expected = List.of(student1, student2);
        assertEquals(expected, jdbcStudentDao.getByGroupId(2));
    }

    @Test
    void getALLWithoutGroup_shouldReturnAllStudentsWhereGroupIdIsNull() {
        List<Student> expected = List.of(jdbcStudentDao.getById(4));
        assertEquals(expected, jdbcStudentDao.getAllWithoutGroup());
    }

    @Test
    void addStudentToGroup_shouldSetArgsGroupIdToArgsStudent() {
        int studentId = 4;
        int groupId = 2;
        assertNull(jdbcStudentDao.getById(studentId).getGroup());
        jdbcStudentDao.addStudentToGroup(studentId, groupId);
        assertNotNull(jdbcStudentDao.getById(studentId).getGroup());
        assertTrue(jdbcStudentDao.getById(studentId).getGroup().getId() == groupId);
    }

    @Test
    void removeStudentFromGroup_shouldSetGroupIdAsNullInArgumentStudent() {
        int testStudentId = 3;
        assertNotNull(jdbcStudentDao.getById(testStudentId).getGroup());
        assertTrue(jdbcStudentDao.removeStudentFromGroup(testStudentId));
        assertNull(jdbcStudentDao.getById(testStudentId).getGroup());
    }
}