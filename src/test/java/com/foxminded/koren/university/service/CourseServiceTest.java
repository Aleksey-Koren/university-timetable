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
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.dao.interfaces.CourseDao;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Course;

@SpringJUnitConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CourseServiceTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Mock
    @Autowired
    private CourseDao mockCourseDao;
    
    @Autowired
    @InjectMocks
    private CourseService courseService;

    @Test
    void createNew_shouldInvokeSaveInCourseDao() {
        Course testCourse = new Course();
        courseService.createNew(testCourse);
        Mockito.verify(mockCourseDao, Mockito.times(1)).save(testCourse);
    }
    
    @Test
    void update_shouldInvokeUpdateInCourseDao() {
        Course testCourse = new Course();
        courseService.update(testCourse);
        Mockito.verify(mockCourseDao, Mockito.times(1)).update(testCourse);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInCourseDao() {
        int testId = 1;
        courseService.deleteById(testId);
        Mockito.verify(mockCourseDao, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInCourseDao() {
        int testId = 1;
        courseService.getById(testId);
        Mockito.verify(mockCourseDao, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInCourseDao() {
        courseService.getAll();
        Mockito.verify(mockCourseDao, Mockito.times(1)).getAll();
    }
}