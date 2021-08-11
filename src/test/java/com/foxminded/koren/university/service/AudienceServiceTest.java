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
import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.dao.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Audience;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class AudienceServiceTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Autowired
    @Mock
    private AudienceDao mockAudienceDao;
    
    @InjectMocks
    @Autowired
    private AudienceService audienceService;
    
    @Test
    void createNew_shouldWorkCorrectly() {
        Audience testAudience = new Audience();
        audienceService.createNew(testAudience);
        Mockito.verify(mockAudienceDao, Mockito.times(1)).save(testAudience);
    }
    
    @Test
    void update_shouldWorkCorrectly() {
        Audience testAudience = new Audience();
        audienceService.update(testAudience);
        Mockito.verify(mockAudienceDao, Mockito.times(1)).update(testAudience);
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() {
        int testId = 1;
        audienceService.deleteById(testId);
        Mockito.verify(mockAudienceDao, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldWorkCorrectly() {
        int testId = 1;
        audienceService.getById(testId);
        Mockito.verify(mockAudienceDao, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldWorkCorrectly() {
        audienceService.getAll();
        Mockito.verify(mockAudienceDao, Mockito.times(1)).getAll();
    }
}