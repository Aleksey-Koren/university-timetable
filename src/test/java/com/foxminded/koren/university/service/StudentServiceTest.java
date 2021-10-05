package com.foxminded.koren.university.service;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.interfaces.StudentRepository;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Student;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class StudentServiceTest {

    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Mock
    @Autowired
    private StudentRepository mockStudentDao;
    
    @InjectMocks
    @Autowired
    private StudentService mockStudentService;
    
    @Test
    void createNew_shouldInvokeSaveInStudentDao() {
        Student testStudent = new Student();
        mockStudentService.createNew(testStudent);
        verify(mockStudentDao, times(1)).save(testStudent);
    }
    
    @Test
    void update_shouldInvokeUpdateInStudentDao() {
        Student testStudent = new Student();
        mockStudentService.update(testStudent);
        verify(mockStudentDao, times(1)).update(testStudent);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInStudentDao() {
        int testId = 1;
        mockStudentService.deleteById(testId);
        verify(mockStudentDao, times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInStudentDao() {
        int testId = 1;
        mockStudentService.getById(testId);
        verify(mockStudentDao, times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInStudentDao() {
        mockStudentService.getAll();
        verify(mockStudentDao, times(1)).getAll();
    }

    @Test
    void getByGroupId_shouldInvokeGetByGroupIdOfStudentDao() {
        mockStudentService.getByGroupId(1);
        verify(mockStudentDao, times(1)).getByGroupId(1);
    }

    @Test
    void getAllWithoutGroup_shouldInvokeGetAllWithoutStudentsOfStudentDao() {
        mockStudentService.getAllWithoutGroup();
        verify(mockStudentDao, times(1)).getAllWithoutGroup();
    }

    @Test
    void addStudentToGroup_shouldInvokeAddStudentToGroupOfStudentDao() {
        mockStudentService.addStudentToGroup(1, 2);
        verify(mockStudentDao, times(1)).addStudentToGroup(1, 2);
    }

    @Test
    void removeStudentFromGroup_shouldInvokeRemoveStudentFromGroupOfStudentDao() {
        mockStudentService.removeStudentFromGroup(1);
        verify(mockStudentDao, times(1)).removeStudentFromGroup(1);
    }
}