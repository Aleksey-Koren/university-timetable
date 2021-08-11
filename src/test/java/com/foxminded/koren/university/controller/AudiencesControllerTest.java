package com.foxminded.koren.university.controller;


import com.foxminded.koren.university.config.SpringConfig;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.AudienceService;
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
import org.springframework.web.servlet.ModelAndView;

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
class AudiencesControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private AudienceService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AudiencesController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestAudiences());
        MvcResult mvcResult = mockMvc.perform(get("/audiences"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        assertEquals("audiences/index", mav.getViewName());
        assertEquals(retrieveTestAudiences(), mav.getModel().get("audiences"));
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestAudiences());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/audiences"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    @Test
    void index_shouldThrowAnException_IfServiceReturnsEmptyList() throws Exception {
        when(mockedService.getAll()).thenReturn(new ArrayList<Audience>());
        mockMvc.perform(get("/audiences"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEntitiesInDatabaseException))
                .andExpect(result -> assertTrue(result.getResolvedException().
                        getMessage().equals("There is no any audiences in database")));
    }

    private List<Audience> retrieveTestAudiences() {
        return List.of(new Audience(21, 50), new Audience(31, 50));
    }
}