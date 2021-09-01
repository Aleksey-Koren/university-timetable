package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.TeacherService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
public class TeachersControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private TeacherService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TeachersController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestTeachers());
        MvcResult mvcResult = mockMvc.perform(get("/teachers"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestTeachers(), mvcResult.getModelAndView().getModel().get("teachers"));
        assertEquals("teachers/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestTeachers());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/teachers"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    private List<Teacher> retrieveTestTeachers() {
        return List.of(new Teacher(), new Teacher(), new Teacher());
    }
}