package com.foxminded.koren.university.service;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.repository.interfaces.StudentRepository;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class StudentServiceTest {

    @Mock
    @Autowired
    @Qualifier("studentRepositoryImpl")
    private StudentRepository mockStudentRepository;
    
    @InjectMocks
    @Autowired
    private StudentService mockStudentService;
    
    @Test
    void createNew_shouldInvokeSaveInStudentDao() {
        Student testStudent = new Student();
        mockStudentService.createNew(testStudent);
        verify(mockStudentRepository, times(1)).save(testStudent);
    }
    
    @Test
    void update_shouldInvokeUpdateInStudentDao() {
        Student testStudent = new Student();
        mockStudentService.update(testStudent);
        verify(mockStudentRepository, times(1)).update(testStudent);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInStudentDao() {
        int testId = 1;
        mockStudentService.deleteById(testId);
        verify(mockStudentRepository, times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInStudentDao() {
        int testId = 1;
        mockStudentService.getById(testId);
        verify(mockStudentRepository, times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInStudentDao() {
        mockStudentService.getAll();
        verify(mockStudentRepository, times(1)).getAll();
    }

    @Test
    void getByGroupId_shouldInvokeGetByGroupIdOfStudentDao() {
        mockStudentService.getByGroupId(1);
        verify(mockStudentRepository, times(1)).getByGroupId(1);
    }

    @Test
    void getAllWithoutGroup_shouldInvokeGetAllWithoutStudentsOfStudentDao() {
        mockStudentService.getAllWithoutGroup();
        verify(mockStudentRepository, times(1)).getAllWithoutGroup();
    }

    @Test
    void addStudentToGroup_shouldInvokeAddStudentToGroupOfStudentDao() {
        mockStudentService.addStudentToGroup(1, 2);
        verify(mockStudentRepository, times(1)).addStudentToGroup(1, 2);
    }

    @Test
    void removeStudentFromGroup_shouldInvokeRemoveStudentFromGroupOfStudentDao() {
        mockStudentService.removeStudentFromGroup(1);
        verify(mockStudentRepository, times(1)).removeStudentFromGroup(1);
    }
}