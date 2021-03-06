package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Year;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {Application.class})
class GroupRepositoryImplTest {

    @Autowired
    @Qualifier("groupRepositoryImpl")
    private GroupRepository groupRepository;
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
        Group expected = new Group("group name1", Year.FIRST);
        expected.setId(expectedId);
        assertEquals(expected, groupRepository.getById(expectedId));
    }

    @Test
    void getAll_shouldWorkCorrectly() {
        Group group1 = new Group("group name1", Year.FIRST);
        group1.setId(1);
        Group group2 = new Group("group name2", Year.SECOND);
        group2.setId(2);
        Group group3 = new Group("group name3", Year.THIRD);
        group3.setId(3);
        List<Group> expected = List.of(group1, group2, group3);
        assertEquals(expected, groupRepository.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() {        
        int expectedId = 4;
        Group expected = new Group("test!!!", Year.SECOND);
        groupRepository.save(expected);
        assertEquals(expected, groupRepository.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        int expectedId = 1;
        Group expected = groupRepository.getById(expectedId);
        expected.setName("changed name");
        expected.setYear(Year.SECOND);
        groupRepository.update(expected);
        assertEquals(expected, groupRepository.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int expectedId = 1;
        Group group = groupRepository.getById(expectedId);
        groupRepository.deleteById(group.getId());
        assertThrows(RepositoryException.class, () -> groupRepository.getById(expectedId));
    }

    @Test
    void getGroupsExceptAdded_shouldGetGroupsExceptAddedToLecture() {
        int lectureId = 1;
        List<Group> allGroups = groupRepository.getAll();
        List<Group> groupAddedToLecture = List.of(new Group(1,"group name1", Year.FIRST),
                new Group(2, "group name2", Year.SECOND));
        allGroups.removeAll(groupAddedToLecture);
        assertEquals(allGroups, groupRepository.getAllGroupsExceptAddedToLecture(lectureId));
    }
}