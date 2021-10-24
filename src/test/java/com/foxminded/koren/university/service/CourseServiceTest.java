package com.foxminded.koren.university.service;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.repository.interfaces.CourseRepository;
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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CourseServiceTest {

    @Mock
    @Autowired
    @Qualifier("courseRepositoryImpl")
    private CourseRepository courseRepository;
    
    @Autowired
    @InjectMocks
    private CourseService courseService;

    @Test
    void createNew_shouldInvokeSaveInCourseDao() {
        Course testCourse = new Course();
        courseService.createNew(testCourse);
        Mockito.verify(courseRepository, Mockito.times(1)).save(testCourse);
    }
    
    @Test
    void update_shouldInvokeUpdateInCourseDao() {
        Course testCourse = new Course();
        courseService.update(testCourse);
        Mockito.verify(courseRepository, Mockito.times(1)).update(testCourse);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInCourseDao() {
        int testId = 1;
        courseService.deleteById(testId);
        Mockito.verify(courseRepository, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInCourseDao() {
        int testId = 1;
        courseService.getById(testId);
        Mockito.verify(courseRepository, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInCourseDao() {
        courseService.getAll();
        Mockito.verify(courseRepository, Mockito.times(1)).getAll();
    }
}