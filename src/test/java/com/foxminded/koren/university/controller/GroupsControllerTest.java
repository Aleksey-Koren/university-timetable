package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.config.SpringConfig;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Year;
import com.foxminded.koren.university.service.GroupService;
import com.foxminded.koren.university.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfig.class})
@ExtendWith(MockitoExtension.class)
public class GroupsControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private GroupService mockedGroupService;

    @Mock
    @Autowired
    private StudentService mockedStudentService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new GroupsController(mockedGroupService, mockedStudentService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedGroupService.getAll()).thenReturn(retrieveTestGroups());
        MvcResult mvcResult = mockMvc.perform(get("/groups"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestGroups(), mvcResult.getModelAndView().getModel().get("groups"));
        assertEquals("groups/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedGroupService.getAll()).thenReturn(retrieveTestGroups());
        InOrder inOrder = inOrder(mockedGroupService);
        mockMvc.perform(get("/groups"));
        inOrder.verify(mockedGroupService, times(1)).getAll();
    }

    private List<Group> retrieveTestGroups() {
        return List.of(new Group("name1", Year.FIRST), new Group("name2", Year.SECOND));
    }
}