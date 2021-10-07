package com.foxminded.koren.university.service;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.interfaces.TeacherRepository;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Teacher;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class TeacherServiceTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Mock
    @Autowired
    @Qualifier("teacherRepositoryImpl")
    private TeacherRepository mockTeacherRepository;
    
    @Autowired
    @InjectMocks
    private TeacherService teacherService;
    
    @Test
    void createNew_shouldInvokeSaveInTeacherDao() {
        Teacher testTeacher = new Teacher();
        teacherService.createNew(testTeacher);
        Mockito.verify(mockTeacherRepository, Mockito.times(1)).save(testTeacher);
    }
    
    @Test
    void update_shouldInvokeUpdateInTeacherDao() {
        Teacher testTeacher = new Teacher ();
        teacherService.update(testTeacher);
        Mockito.verify(mockTeacherRepository, Mockito.times(1)).update(testTeacher);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInTeacherDao() {
        int testId = 1;
        teacherService.deleteById(testId);
        Mockito.verify(mockTeacherRepository, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInTeacherDao() {
        int testId = 1;
        teacherService.getById(testId);
        Mockito.verify(mockTeacherRepository, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInTeacherDao() {
        teacherService.getAll();
        Mockito.verify(mockTeacherRepository, Mockito.times(1)).getAll();
    }
}