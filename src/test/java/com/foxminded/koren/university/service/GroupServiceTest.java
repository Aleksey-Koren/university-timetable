package com.foxminded.koren.university.service;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
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
class GroupServiceTest {

    @Mock
    @Autowired
    @Qualifier("groupRepositoryImpl")
    private GroupRepository mockGroupRepository;
    
    @Autowired
    @InjectMocks
    private GroupService groupService;
    
    @Test
    void createNew_shouldInvokeSaveInGroupDao() {
       Group testGroup = new Group();
       groupService.createNew(testGroup);
       Mockito.verify(mockGroupRepository, Mockito.times(1)).save(testGroup);
    }
    
    @Test
    void update_shouldInvokeUpdateInGroupDao() {
        Group testGroup = new Group();
        groupService.update(testGroup);
        Mockito.verify(mockGroupRepository, Mockito.times(1)).update(testGroup);
    }
    
    @Test
    void deleteById_shouldInvokeDeleteByIdInGroupDao() {
        int testId = 1;
        groupService.deleteById(testId);
        Mockito.verify(mockGroupRepository, Mockito.times(1)).deleteById(testId);
    }
    
    @Test
    void getById_shouldInvokeGetByIdInGroupDao() {
        int testId = 1;
        groupService.getById(testId);
        Mockito.verify(mockGroupRepository, Mockito.times(1)).getById(testId);
    }
    
    @Test
    void getAll_shouldInvokeGetAllInGroupDao() {
        groupService.getAll();
        Mockito.verify(mockGroupRepository, Mockito.times(1)).getAll();
    }
    
    @Test
    void releaseGroup_shouldInvokeDeleteByIdInGroupDaoOnCurrentGroupId() {
        Group testGroup = new Group();
        Integer testId = 1;
        testGroup.setId(testId);
        groupService.releaseGroup(testGroup);
        Mockito.verify(mockGroupRepository, Mockito.times(1)).deleteById(testGroup.getId());
    }
}