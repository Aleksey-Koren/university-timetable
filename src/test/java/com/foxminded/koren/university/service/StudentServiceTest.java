package com.foxminded.koren.university.service;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

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
    private StudentDao mockStudentDao;
    
    @InjectMocks
    @Autowired
    private StudentService studentService;
    
    @Test
    void createNew_shouldInvokeSaveInStudentDao() {
        Student testStudent = new Student();
        studentService.createNew(testStudent);
        Mockito.verify(mockStudentDao, Mockito.times(1)).save(testStudent);
    }
    
    @Test
    void update_shouldInvokeUpdateInStudentDao() {
        Student testStudent = new Student();
        studentService.update(testStudent);
        Mockito.verify(mockStudentDao, Mockito.times(1)).update(testStudent);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInStudentDao() {
        int testId = 1;
        studentService.deleteById(testId);
        Mockito.verify(mockStudentDao, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInStudentDao() {
        int testId = 1;
        studentService.getById(testId);
        Mockito.verify(mockStudentDao, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInStudentDao() {
        studentService.getAll();
        Mockito.verify(mockStudentDao, Mockito.times(1)).getAll();
    }
}