package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

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

    @Captor
    ArgumentCaptor<Teacher> teacherArgumentCaptor;

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

    @Test
    void newTeacher_shouldReturnRightWViewAndAddEmptyTeacherToModel() throws Exception {
        Teacher expected = new Teacher();
        MvcResult result = mockMvc.perform(get("/teachers/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        assertEquals("teachers/new", mav.getViewName());
        assertEquals(expected, mav.getModel().get("teacher"));
    }

    @Test
    void create_shouldInvokeCreateNewMethodInServiceWithExpectedParamAndRedirectCorrectly() throws Exception {
        Teacher expected = new Teacher (0, "first name", "last name");

        MockHttpServletRequestBuilder request = post("/teachers/new-create")
                .param("firstName", expected.getFirstName())
                .param("lastName",expected.getLastName());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/teachers"))
                .andReturn();
        verify(mockedService).createNew(teacherArgumentCaptor.capture());
        assertEquals(expected, result.getModelAndView().getModel().get("teacher"));
    }

    @Test
    void edit_shouldReturnCorrectViewAndInvokeGetByIdAndAddAttributesToModel() throws Exception {
        Teacher expected = new Teacher (2, "first name", "last name");
        when(mockedService.getById(expected.getId())).thenReturn(expected);
        InOrder inOrder = inOrder(mockedService);
        MvcResult result = mockMvc.perform(get("/teachers/{id}/edit", expected.getId())).andReturn();
        ModelAndView mav = result.getModelAndView();
        assertEquals("teachers/edit", mav.getViewName());
        assertEquals(expected, mav.getModel().get("teacher"));
    }

    @Test
    void update_shouldInvokeUpdateMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        Teacher expected = new Teacher (2, "first name", "last name");
        MockHttpServletRequestBuilder request = post("/teachers/{id}/edit-update", expected.getId())
                .param("firstName", expected.getFirstName())
                .param("lastName", expected.getLastName());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/teachers"))
                .andReturn();
        verify(mockedService).update(teacherArgumentCaptor.capture());
        assertEquals(expected, result.getModelAndView().getModel().get("teacher"));
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/teachers/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedService, times(1)).deleteById(expectedId);
    }

    private List<Teacher> retrieveTestTeachers() {
        return List.of(new Teacher(), new Teacher(), new Teacher());
    }
}