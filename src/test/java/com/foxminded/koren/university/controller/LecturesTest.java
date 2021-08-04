package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.config.SpringConfig;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.*;
import com.foxminded.koren.university.service.LectureService;
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
@ContextConfiguration(classes = {SpringConfig.class})
@ExtendWith(MockitoExtension.class)
class LecturesTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private LectureService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LecturesController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestLectures());
        MvcResult mvcResult = mockMvc.perform(get("/lectures"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestLectures(), mvcResult.getModelAndView().getModel().get("lectures"));
        assertEquals("lectures/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestLectures());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/lectures"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    @Test
    void index_shouldThrowAnException_IfServiceReturnsEmptyList() throws Exception {
        when(mockedService.getAll()).thenReturn(new ArrayList<Lecture>());
        mockMvc.perform(get("/lectures"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntitiesInDatabaseException))
                .andExpect(result -> assertTrue(result.getResolvedException().
                        getMessage().equals("There is no any lectures in database")));
    }

    private List<Lecture> retrieveTestLectures() {
        return List.of(new Lecture(), new Lecture(), new Lecture());
    }
}
