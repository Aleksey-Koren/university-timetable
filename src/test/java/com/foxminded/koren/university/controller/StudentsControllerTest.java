package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.controller.dto.StudentGetDTO;
import com.foxminded.koren.university.controller.dto.StudentPostDTO;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.service.GroupService;
import com.foxminded.koren.university.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {Application.class})
@ExtendWith(MockitoExtension.class)
public class StudentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private StudentService mockedStudentService;
    @Mock
    @Autowired
    private GroupService mockedGroupService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new StudentsController(mockedStudentService, mockedGroupService)).build();
    }

    @Captor
    ArgumentCaptor<Student> studentCaptor;

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;
    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        when(mockedStudentService.getAll()).thenReturn(retrieveTestStudents());
        MvcResult mvcResult = mockMvc.perform(get("/students"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(retrieveTestStudents(), mvcResult.getModelAndView().getModel().get("students"));
        assertEquals("students/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedStudentService.getAll()).thenReturn(retrieveTestStudents());
        InOrder inOrder = inOrder(mockedStudentService);
        mockMvc.perform(get("/students"));
        inOrder.verify(mockedStudentService, times(1)).getAll();
    }

    @Test
    void newStudent_shouldReturnRightWViewAndAddStudentToModel() throws Exception {
        Student student = new Student();
        MvcResult result = mockMvc.perform(get("/students/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(student, modelAndView.getModel().get("student"));
        assertEquals("students/new", modelAndView.getViewName());
    }

    @Test
    void create_shouldInvokeCreateNewMethodInServiceWithExpectedParamAndRedirectCorrectly() throws Exception {
        Student expected = new Student("firstName", "lastName");
        int expectedId = 0;
        MockHttpServletRequestBuilder request = post("/students/new-create")
                .param("firstName", expected.getFirstName())
                .param("lastName",expected.getLastName());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrlTemplate("/students/{id}/edit", expectedId))
                .andReturn();
        verify(mockedStudentService).createNew(studentCaptor.capture());
        assertEquals(expected, result.getModelAndView().getModel().get("student"));
        expected.setId(studentCaptor.getValue().getId());
        assertEquals(expected, studentCaptor.getValue());
    }

    @Test
    void edit_shouldReturnCorrectViewAndAddAttributesToModelAndInvokeServicesMethodsCorrectly() throws Exception {
        int testId = 1;
        Student expected = new Student(testId, new Group(1), "firstName", "lastName");
        StudentPostDTO formDTO = new StudentPostDTO(expected.getId(), expected.getGroup().getId(),
                expected.getFirstName(), expected.getLastName());
        when(mockedStudentService.getById(testId)).thenReturn(expected);
        MvcResult result = mockMvc.perform(get("/students/{id}/edit", testId))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        verify(mockedStudentService).getById(testId);
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("students/edit", modelAndView.getViewName());
        assertEquals(expected, modelAndView.getModel().get("student"));
        assertEquals(formDTO, modelAndView.getModel().get("formDTO"));
    }

    @Test
    void update_shouldInvokeUpdateMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        int testId = 1;
        Student expected = new Student(testId, new Group(1), "firstName", "lastName");
        StudentPostDTO formDTO = new StudentPostDTO(expected.getId(), expected.getGroup().getId(),
                expected.getFirstName(), expected.getLastName());
        MockHttpServletRequestBuilder request = post("/students/{id}/edit-update", testId)
                .param("groupId", String.valueOf(expected.getGroup().getId()))
                .param("firstName", expected.getFirstName())
                .param("lastName", expected.getLastName());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/students"))
                .andReturn();
        verify(mockedStudentService).update(studentCaptor.capture());
        assertEquals(expected, studentCaptor.getValue());
        assertEquals(formDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void selectGroup_shouldAddAttributesToModelAndReturnExpectedView() throws Exception {
        int testId = 5;
        Student expected = new Student(testId, new Group(1), "firstName", "lastName");
        List<Group> testGroups = List.of(new Group(1), new Group(2), new Group(3));
        StudentGetDTO dto = new StudentGetDTO(expected, testGroups);
        when(mockedStudentService.getById(testId)).thenReturn(expected);
        when(mockedGroupService.getAll()).thenReturn(testGroups);
        MvcResult result = mockMvc.perform(get("/students/{id}/edit-select", testId))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("students/edit-select", modelAndView.getViewName());
        assertEquals(dto, modelAndView.getModel().get("dto"));
        assertEquals(new StudentPostDTO(), modelAndView.getModel().get("formDTO"));
    }

    @Test
    void addStudentToGroup_shouldInvokeServiceMethodWithCorrectArgs() throws Exception {
        int testStudentId = 2;
        int testGroupId = 4;
        StudentPostDTO formDTO = new StudentPostDTO();
        formDTO.setGroupId(testGroupId);
        MockHttpServletRequestBuilder request = post("/students/{id}/edit-add", testStudentId)
                .param("groupId", String.valueOf(testGroupId));
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrlTemplate("/students/{id}/edit", testStudentId))
                .andReturn();
        verify(mockedStudentService).addStudentToGroup(integerArgumentCaptor.capture(),
                integerArgumentCaptor.capture());
        assertEquals(testStudentId, integerArgumentCaptor.getAllValues().get(0));
        assertEquals(testGroupId, integerArgumentCaptor.getAllValues().get(1));
        assertEquals(formDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void removeStudentFromGroup_shouldInvokeServiceMethodWithCorrectArgs() throws Exception {
        int testStudentId = 2;
        int testGroupId = 4;
        StudentPostDTO formDTO = new StudentPostDTO();
        formDTO.setGroupId(testGroupId);
        MockHttpServletRequestBuilder request = post("/students/{id}/edit-remove", testStudentId)
                .param("groupId", String.valueOf(testGroupId));
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrlTemplate("/students/{id}/edit", testStudentId))
                .andReturn();
        verify(mockedStudentService).removeStudentFromGroup(integerArgumentCaptor.capture());
        assertEquals(testStudentId, integerArgumentCaptor.getValue());
        assertEquals(formDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/students/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedStudentService).deleteById(expectedId);
    }


    private List<Student> retrieveTestStudents() {
        return List.of(new Student(), new Student(), new Student());
    }
}