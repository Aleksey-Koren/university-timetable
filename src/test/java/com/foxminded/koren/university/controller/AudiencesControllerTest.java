package com.foxminded.koren.university.controller;


import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.AudienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest(classes = {Application.class})
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
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertEquals("audiences/index", modelAndView.getViewName());
        assertEquals(retrieveTestAudiences(), modelAndView.getModel().get("audiences"));
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestAudiences());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/audiences"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    @Test
    void newAudience_shouldAddNewAudienceIntoModelAndSendItWithRightView() throws Exception {
        MvcResult result = mockMvc.perform(get("/audiences/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("audiences/new", modelAndView.getViewName());
        assertEquals(new Audience(), modelAndView.getModel().get("audience"));
    }

    @Test
    void create_shouldInvokeCreateNewMethodInServiceAndRedirectCorrectly() throws Exception {
        String number = "42";
        String capacity = "35";
        Audience expected = new Audience(parseInt(number), parseInt(capacity));
        InOrder inOrder = inOrder(mockedService);
        MockHttpServletRequestBuilder request = post("/audiences/new-create")
                .param("number", number).param("capacity", capacity);
        MvcResult result =  mockMvc.perform(request).andExpect(redirectedUrl("/audiences")).andReturn();
        assertEquals(expected, result.getModelAndView().getModel().get("audience"));
        inOrder.verify(mockedService, times(1)).createNew(any());
    }
    @Test
    void edit_shouldReturnCorrectViewAndInvokeGetByIdAndAddAttributesToModel () throws Exception {
        int testId = 8;
        Audience expected = new Audience(132, 35);
        when(mockedService.getById(8)).thenReturn(expected);
        InOrder inOrder = inOrder(mockedService);
        MvcResult result = mockMvc.perform(get("/audiences/8/edit")).andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("audiences/edit", modelAndView.getViewName());
        assertEquals(expected, modelAndView.getModel().get("audience"));
        inOrder.verify(mockedService, times(1)).getById(8);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void update_shouldInvokeUpdateMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        Audience expected = new Audience(1, 10, 10);
        MockHttpServletRequestBuilder request = post("/audiences/1/edit-update")
                .param("id", "1")
                .param("number", "10")
                .param("capacity", "10");
        InOrder inOrder = inOrder(mockedService);
        MvcResult result = mockMvc.perform(request).andExpect(redirectedUrl("/audiences")).andReturn();
        assertEquals(expected, result.getModelAndView().getModel().get("audience"));
        inOrder.verify(mockedService).update(expected);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/audiences/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedService, times(1)).deleteById(expectedId);
    }

    private List<Audience> retrieveTestAudiences() {
        return List.of(new Audience(21, 50), new Audience(31, 50));
    }
}