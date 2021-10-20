package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CoursesControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private CourseService mockedService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CoursesController(mockedService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestCourses());
        MvcResult mvcResult = mockMvc.perform(get("/courses"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestCourses(), mvcResult.getModelAndView().getModel().get("courses"));
        assertEquals("courses/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedService.getAll()).thenReturn(retrieveTestCourses());
        InOrder inOrder = inOrder(mockedService);
        mockMvc.perform(get("/courses"));
        inOrder.verify(mockedService, times(1)).getAll();
    }

    @Test
    void newCourse_shouldReturnRightWViewAndAddEmptyCourseToModel() throws Exception {
        Course expected = new Course();
        MvcResult result = mockMvc.perform(get("/courses/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("courses/new", modelAndView.getViewName());
        assertEquals(expected, modelAndView.getModel().get("course"));
    }

    @Test
    void edit_shouldReturnCorrectViewAndInvokeGetByIdAndAddAttributesToModel() throws Exception {
        Course expected = new Course (2, "test name", "test description");
        when(mockedService.getById(expected.getId())).thenReturn(expected);
        InOrder inOrder = inOrder(mockedService);
        MvcResult result = mockMvc.perform(get("/courses/{id}/edit", expected.getId())).andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("courses/edit", modelAndView.getViewName());
        assertEquals(expected, modelAndView.getModel().get("course"));
    }

    @Test
    void update_shouldInvokeUpdateMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        InOrder inOrder = inOrder(mockedService);
        Course expected = new Course (2, "test name", "test description");
        MockHttpServletRequestBuilder request = post("/courses/{id}/edit-update", expected.getId())
                .param("name", expected.getName())
                .param("description", expected.getDescription());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/courses"))
                .andReturn();
        assertEquals(expected, result.getModelAndView().getModel().get("course"));
        inOrder.verify(mockedService).update(expected);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/courses/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedService, times(1)).deleteById(expectedId);
    }

        private List<Course> retrieveTestCourses() {
        return List.of(new Course("name1", "description1"),
                       new Course("name2", "description2"));
    }
}