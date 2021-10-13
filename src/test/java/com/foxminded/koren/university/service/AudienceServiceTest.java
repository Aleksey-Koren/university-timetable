package com.foxminded.koren.university.service;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.repository.interfaces.AudienceRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class AudienceServiceTest {
    

    @Mock
    @Autowired
    @Qualifier("audienceRepositoryImpl")
    private AudienceRepository mockAudienceDao;
    
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