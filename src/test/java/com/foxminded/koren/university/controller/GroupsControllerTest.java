package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.controller.dto.GroupGetDTO;
import com.foxminded.koren.university.controller.dto.GroupPostDTO;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class GroupsControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private GroupService mockedGroupService;

    @Mock
    @Autowired
    private StudentService mockedStudentService;

    @Captor
    ArgumentCaptor<Group> groupArgumentCaptor;

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

    @Test
    void newGroup_shouldReturnRightWViewAndAddDtosToModel() throws Exception {
        GroupGetDTO dto = new GroupGetDTO.Builder().years().build();
        GroupPostDTO formDTO = new GroupPostDTO();
        MvcResult result = mockMvc.perform(get("/groups/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(dto, modelAndView.getModel().get("dto"));
        assertEquals(formDTO, modelAndView.getModel().get("formDTO"));
        assertEquals("groups/new", modelAndView.getViewName());
    }

    @Test
    void create_shouldInvokeCreateNewMethodInServiceWithExpectedParamAndRedirectCorrectly() throws Exception {
        Group expectedArgument = new Group("group name", Year.FIRST);
        Group expectedReturnValue = new Group(2, "group name", Year.FIRST);
        when(mockedGroupService.createNew(any())).thenReturn(expectedReturnValue);
        GroupPostDTO postDTO = new GroupPostDTO.Builder()
                .groupName("group name")
                .year(Year.FIRST).build();
        MockHttpServletRequestBuilder request = post("/groups/new-create")
                .param("groupName", postDTO.getGroupName())
                .param("year", postDTO.getYear().toString());
        InOrder inOrder = inOrder(mockedGroupService);
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrlTemplate("/groups/{id}/edit", expectedReturnValue.getId()))
                .andReturn();
        verify(mockedGroupService, times(1)).createNew(groupArgumentCaptor.capture());
        assertEquals(expectedArgument, groupArgumentCaptor.getValue());
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(postDTO, modelAndView.getModel().get("formDTO"));
    }

    @Test
    void edit_shouldReturnCorrectViewAndAddAttributesToModelAndInvokeServicesMethodsCorrectly() throws Exception {
        int testGroupId = 2;
        Group testGroup = retrieveTestGroup();
        when(mockedGroupService.getById(testGroupId)).thenReturn(testGroup);
        when(mockedStudentService.getByGroupId(testGroupId)).thenReturn(retrieveTestStudents());
        GroupGetDTO testGetDTO = retrieveTestDto();
        GroupPostDTO testPostDTO = new GroupPostDTO(testGroup.getName(), testGroup.getYear());

        InOrder inOrder = inOrder(mockedGroupService, mockedStudentService);
        MvcResult result = mockMvc.perform(get("/groups/{id}/edit", testGroupId))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("groups/edit", modelAndView.getViewName());
        assertEquals(testGetDTO, modelAndView.getModel().get("dto"));
        assertEquals(testPostDTO, modelAndView.getModel().get("formDTO"));
        inOrder.verify(mockedGroupService, times(1)).getById(testGroupId);
        inOrder.verify(mockedStudentService, times(1)).getByGroupId(testGroupId);
    }

    @Test
    void update_shouldInvokeUpdateMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        int testGroupId = 2;
        GroupPostDTO testPostDTO = new GroupPostDTO.Builder()
                .groupId(testGroupId)
                .groupName("name2")
                .year(Year.FIRST)
                .build();
        Group testGroup = new Group(testPostDTO.getGroupId(), testPostDTO.getGroupName(), testPostDTO.getYear());
        MockHttpServletRequestBuilder request = post("/groups/{id}/edit-update", testGroupId)
                .param("groupId", "2")
                .param("groupName", "name2")
                .param("year", Year.FIRST.toString());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/groups"))
                .andReturn();
        verify(mockedGroupService, times(1))
                .update(groupArgumentCaptor.capture());
        assertEquals(testGroup, groupArgumentCaptor.getValue());
        assertEquals(testPostDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void selectStudents_shouldAddToModelAttributesAndReturnExpectedView() throws Exception {
        Group testGroup = retrieveTestGroup();
        List<Student> studentsWithoutGroup = retrieveTestStudents();
        GroupGetDTO testGetDTO = new GroupGetDTO.Builder()
                .group(testGroup)
                .studentsWithoutGroup(studentsWithoutGroup)
                .build();
        GroupPostDTO testPostDTO = new GroupPostDTO.Builder()
                .groupId(testGroup.getId())
                .studentsIds(new ArrayList<>())
                .build();
        when(mockedStudentService.getAllWithoutGroup()).thenReturn(studentsWithoutGroup);
        when(mockedGroupService.getById(testGroup.getId())).thenReturn(testGroup);
        MvcResult result = mockMvc.perform(get("/groups/{id}/edit-select", testGroup.getId()))
                .andExpect(model().attributeHasNoErrors()).andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("groups/edit-select", modelAndView.getViewName());
        assertEquals(testGetDTO, modelAndView.getModel().get("dto"));
        assertEquals(testPostDTO, modelAndView.getModel().get("formDTO"));
    }

    @Test
    void addStudents_shouldInvokeAddStudentMethodOfServiceRequiredNumberOfTimesAndRedirectToCorrectUrl() throws Exception {
        int testGroupId = 2;
        GroupPostDTO testPostDto = new GroupPostDTO.Builder()
                .groupId(testGroupId)
                .studentsIds(List.of(1, 2, 3))
                .build();
        MockHttpServletRequestBuilder request = post("/groups/{id}/edit-add", testGroupId)
                .param("groupId", "2")
                .param("_studentsIds", "on")
                .param("studentsIds", "1")
                .param("_studentsIds", "on")
                .param("studentsIds", "2")
                .param("_studentsIds", "on")
                .param("studentsIds", "3");

        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrlTemplate("/groups/{id}/edit", testGroupId))
                .andReturn();
        GroupPostDTO returnValue = (GroupPostDTO) result.getModelAndView().getModel().get("formDTO");
        assertEquals(testPostDto, returnValue);
        verify(mockedStudentService, times(returnValue.getStudentsIds().size())).addStudentToGroup(anyInt(), anyInt());
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/groups/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedGroupService).deleteById(expectedId);
    }

    private List<Group> retrieveTestGroups() {
        return List.of(new Group("name1", Year.FIRST), new Group("name2", Year.SECOND));
    }

    private Group retrieveTestGroup() {
        return new Group(2, "group name", Year.FIRST);
    }


    private List<Student> retrieveTestStudents() {
        Student student1 = new Student(1, new Group(2), "fName1", "lName1");
        Student student2 = new Student(2, new Group(2), "fName2", "lName2");
        return List.of(student1,student2);
    }

    private GroupGetDTO retrieveTestDto() {
        return new GroupGetDTO.Builder()
                .group(retrieveTestGroup())
                .students(retrieveTestStudents())
                .years()
                .build();
    }
}