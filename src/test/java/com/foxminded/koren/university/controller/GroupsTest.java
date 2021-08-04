package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.config.SpringConfig;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.GroupService;
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
public class GroupsTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private GroupService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GroupsController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestGroups());
        MvcResult mvcResult = mockMvc.perform(get("/groups"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestGroups(), mvcResult.getModelAndView().getModel().get("groups"));
        assertEquals("groups/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestGroups());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/groups"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    @Test
    void index_shouldThrowAnException_IfServiceReturnsEmptyList() throws Exception {
        when(mockedService.getAll()).thenReturn(new ArrayList<Group>());
        mockMvc.perform(get("/groups"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntitiesInDatabaseException))
                .andExpect(result -> assertTrue(result.getResolvedException().
                        getMessage().equals("There is no any groups in database")));
    }

    private List<Group> retrieveTestGroups() {
        return List.of(new Group("name1"), new Group("name2"));
    }
}