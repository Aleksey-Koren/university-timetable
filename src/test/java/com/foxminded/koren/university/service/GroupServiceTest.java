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

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
import com.foxminded.koren.university.repository.test_data.TablesCreation;
import com.foxminded.koren.university.entity.Group;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class GroupServiceTest {
    
    @Autowired
    private TablesCreation tablesCreation;
    
    @BeforeAll
    void createTables() throws DataAccessException, IOException {
        tablesCreation.createTables();
    }
    
    @Mock
    @Autowired
    private GroupRepository mockGroupDao;
    
    @Autowired
    @InjectMocks
    private GroupService groupService;
    
    @Test
    void createNew_shouldInvokeSaveInGroupDao() {
       Group testGroup = new Group();
       groupService.createNew(testGroup);
       Mockito.verify(mockGroupDao, Mockito.times(1)).save(testGroup);
    }
    
    @Test
    void update_shouldInvokeUpdateInGroupDao() {
        Group testGroup = new Group();
        groupService.update(testGroup);
        Mockito.verify(mockGroupDao, Mockito.times(1)).update(testGroup);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInGroupDao() {
        int testId = 1;
        groupService.deleteById(testId);
        Mockito.verify(mockGroupDao, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInGroupDao() {
        int testId = 1;
        groupService.getById(testId);
        Mockito.verify(mockGroupDao, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInGroupDao() {
        groupService.getAll();
        Mockito.verify(mockGroupDao, Mockito.times(1)).getAll();
    }
    
    @Test
    void releaseGroup_shouldInvokeDeleteByIdInGroupDaoOnCurrentGroupId() {
        Group testGroup = new Group();
        Integer testId = 1;
        testGroup.setId(testId);
        groupService.releaseGroup(testGroup);
        Mockito.verify(mockGroupDao, Mockito.times(1)).deleteById(testGroup.getId());
    }
}