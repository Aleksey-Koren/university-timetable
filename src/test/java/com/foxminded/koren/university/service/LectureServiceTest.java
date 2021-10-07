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
import com.foxminded.koren.university.repository.interfaces.LectureRepository;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Lecture;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class LectureServiceTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Mock
    @Autowired
    @Qualifier("lectureRepositoryImpl")
    private LectureRepository mockLectureRepository;
    
    @Autowired
    @InjectMocks
    private LectureService lectureService;
    
    @Test
    void createNew_shouldInvokeSaveInLectureDao() {
       Lecture testLecture = new Lecture();
       lectureService.createNew(testLecture);
       Mockito.verify(mockLectureRepository, Mockito.times(1)).save(testLecture);
    }
    
    @Test
    void update_shouldInvokeUpdateInLectureDao() {
        Lecture testLecture = new Lecture();
        lectureService.update(testLecture);
        Mockito.verify(mockLectureRepository, Mockito.times(1)).update(testLecture);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInLectureDao() {
        int testId = 1;
        lectureService.deleteById(testId);
        Mockito.verify(mockLectureRepository, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInLectureDao() {
        int testId = 1;
        lectureService.getById(testId);
        Mockito.verify(mockLectureRepository, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInLectureDao() {
        lectureService.getAll();
        Mockito.verify(mockLectureRepository, Mockito.times(1)).getAll();
    }   
}