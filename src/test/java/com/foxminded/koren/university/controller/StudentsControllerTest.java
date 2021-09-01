package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.config.SpringConfig;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Student;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
public class StudentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private StudentService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new StudentsController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestStudents());
        MvcResult mvcResult = mockMvc.perform(get("/students"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestStudents(), mvcResult.getModelAndView().getModel().get("students"));
        assertEquals("students/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestStudents());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/students"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    private List<Student> retrieveTestStudents() {
        return List.of(new Student(), new Student(), new Student());
    }
}