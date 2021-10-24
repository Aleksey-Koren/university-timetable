package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.Application;
import com.foxminded.koren.university.controller.dto.LectureGetDTO;
import com.foxminded.koren.university.controller.dto.LecturePostDTO;
import com.foxminded.koren.university.entity.*;
import com.foxminded.koren.university.service.*;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest(classes = {Application.class})
@ExtendWith(MockitoExtension.class)
class LecturesControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private LectureService mockedLectureService;
    @Mock
    @Autowired
    private GroupService mockedGroupService;
    @Mock
    @Autowired
    private CourseService mockedCourseService;
    @Mock
    @Autowired
    private AudienceService mockedAudienceService;
    @Mock
    @Autowired
    private TeacherService mockedTeacherService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new LecturesController(mockedLectureService,
                        mockedGroupService, mockedCourseService, mockedAudienceService,
                                            mockedTeacherService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        List<Lecture> testLectures = retrieveTestLectures();
        when(mockedLectureService.getAll()).thenReturn(testLectures);
        List<LectureGetDTO> testDtos = retrieveTestLectureDTOs(testLectures);
        MvcResult mvcResult = mockMvc.perform(get("/lectures"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        assertEquals(testDtos, mvcResult.getModelAndView().getModel().get("dtos"));
        assertEquals("lectures/index", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void index_shouldCallGetAllMethodOfService() throws Exception {
        when(mockedLectureService.getAll()).thenReturn(retrieveTestLectures());
        InOrder inOrder = inOrder(mockedLectureService);
        mockMvc.perform(get("/lectures"));
        inOrder.verify(mockedLectureService, times(1)).getAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void newLecture_shouldAddAttributesToModelAndReturnCorrectView() throws Exception {
        LectureGetDTO dto = retrieveGetDTONew();
        when(mockedCourseService.getAll()).thenReturn(dto.getAllCourses());
        when(mockedAudienceService.getAll()).thenReturn(dto.getAllAudiences());
        when(mockedTeacherService.getAll()).thenReturn(dto.getAllTeachers());
        MvcResult result = mockMvc.perform(get("/lectures/new"))
                .andExpect(model().attributeHasNoErrors())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("lectures/new", modelAndView.getViewName());
        assertEquals(dto, modelAndView.getModel().get("dto"));
    }

    @Test
    void create_shouldAddAttributesToModelAndInvokeServiceMethodAndRedirectToCurrentURL() throws Exception {
        int testId = 2;
        when(mockedLectureService.createNew(any())).thenReturn(new Lecture(testId));
        LecturePostDTO formDTO = retrievePostDTO(retrieveGetDTO());
        MockHttpServletRequestBuilder request = post("/lectures/new-create")
                .param("courseId", formDTO.getCourseId().toString())
                .param("audienceId", formDTO.getAudienceId().toString())
                .param("teacherId", formDTO.getTeacherId().toString())
                .param("startTime", formDTO.getStartTime().toString())
                .param("endTime", formDTO.getEndTime().toString());
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl(String.format("/lectures/%s/edit", 0)))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(formDTO, modelAndView.getModel().get("formDTO"));
    }

    @Test
    void edit_shouldCallMethodsOfServiceAndReturnCorrectView() throws Exception {
        int testId = 2;
        LectureGetDTO dto = retrieveGetDTO();
        LecturePostDTO formDTO = retrievePostDTO(dto);
        when(mockedLectureService.getById(testId)).thenReturn(dto.getLecture());
        when(mockedCourseService.getAll()).thenReturn(dto.getAllCourses());
        when(mockedAudienceService.getAll()).thenReturn(dto.getAllAudiences());
        when(mockedTeacherService.getAll()).thenReturn(dto.getAllTeachers());
        when(mockedGroupService.getAllExceptAddedToLecture(testId)).thenReturn(dto.getAllGroupsExceptAdded());
        MvcResult result = mockMvc.perform(get("/lectures/{id}/edit", testId))
                .andExpect(model().attributeHasNoErrors()).andReturn();
        ModelAndView modelAndView
                = result.getModelAndView();
        assertEquals("lectures/edit", modelAndView
                .getViewName());
        verify(mockedLectureService).getById(testId);
        verify(mockedCourseService).getAll();
        verify(mockedAudienceService).getAll();
        verify(mockedTeacherService).getAll();
        verify(mockedGroupService).getAllExceptAddedToLecture(testId);
        assertEquals(dto, modelAndView
                .getModel().get("dto"));
        assertEquals(formDTO, modelAndView
                .getModel().get("formDTO"));
    }

    @Test
    void update_shouldInvokeGetLectureByIdAndUpdateItsFieldsFromDTOAndInvokeUpdateWithItAndRedirectToExpectedUrl() throws Exception {
        int testId = 2;
        LecturePostDTO formDTO = retrievePostDTO(retrieveGetDTO());
        MockHttpServletRequestBuilder request = post("/lectures/{id}/edit-update", testId)
                .param("courseId", formDTO.getCourseId().toString())
                .param("audienceId", formDTO.getAudienceId().toString())
                .param("teacherId", formDTO.getTeacherId().toString())
                .param("startTime", formDTO.getStartTime().toString())
                .param("endTime", formDTO.getEndTime().toString());
        Lecture lectureFromDatabase = retrieveTestLecture(testId);
        when(mockedLectureService.getById(testId)).thenReturn(lectureFromDatabase);
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl("/lectures"))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(formDTO, modelAndView.getModel().get("formDTO"));
        verify(mockedLectureService).update(lectureFromDatabase);
        assertTrue(lectureFromDatabase.getAudience().getId() == formDTO.getAudienceId() &&
                lectureFromDatabase.getTeacher().getId() == formDTO.getTeacherId() &&
                lectureFromDatabase.getCourse().getId() == formDTO.getCourseId() &&
                lectureFromDatabase.getAudience().getId() == formDTO.getAudienceId() &&
                lectureFromDatabase.getStartTime().equals(formDTO.getStartTime()) &&
                lectureFromDatabase.getEndTime().equals(formDTO.getEndTime()));
    }

    private Lecture retrieveTestLecture(int id) {
        Lecture lecture = new Lecture(id, new Audience(), new Teacher(), new Course(), LocalDateTime.now(), LocalDateTime.now());
        lecture.setGroups(List.of(new Group(1, "name", Year.FIRST)));
        return lecture;
    }

    @Test
    void removeGroup_shouldInvokeRemoveMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        int testId = 2;
        LecturePostDTO formDTO = new LecturePostDTO();
        formDTO.setGroupId(testId);
        MockHttpServletRequestBuilder request = post("/lectures/{id}-remove-group", testId)
                .param("groupId", "2");
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl(String.format("/lectures/%s/edit", testId)))
                .andReturn();
        assertEquals(formDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void addGroup_shouldInvokeRemoveMethodOfServiceWithCorrectModelArgsAndRedirectToExpectedUrl() throws Exception {
        int testId = 2;
        LecturePostDTO formDTO = new LecturePostDTO();
        formDTO.setGroupId(testId);
        MockHttpServletRequestBuilder request = post("/lectures/{id}-add-group", testId)
                .param("groupId", "2");
        MvcResult result = mockMvc.perform(request)
                .andExpect(model().attributeHasNoErrors())
                .andExpect(redirectedUrl(String.format("/lectures/%s/edit", testId)))
                .andReturn();
        assertEquals(formDTO, result.getModelAndView().getModel().get("formDTO"));
    }

    @Test
    void delete_shouldInvokeDeleteByIdOfServiceWithCorrectPathVariable() throws Exception {
        Integer expectedId = 2;
        MockHttpServletRequestBuilder request = post("/lectures/{id}/delete", expectedId);
        mockMvc.perform(request);
        verify(mockedLectureService).deleteById(expectedId);
    }

    private LecturePostDTO retrievePostDTO(LectureGetDTO dto) {
        return new LecturePostDTO(dto.getLecture().getCourse().getId(),
                dto.getLecture().getAudience().getId(),
                dto.getLecture().getTeacher().getId(),
                dto.getLecture().getStartTime(),
                dto.getLecture().getEndTime());
    }

    private LectureGetDTO retrieveGetDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Lecture lecture = new Lecture(2,
                new Audience(1, 20, 20),
                new Teacher(1, "fName1", "lName1"),
                new Course(1, "name", "description"),
                LocalDateTime.parse("2021-09-02T13:48", formatter),
                LocalDateTime.parse("2021-09-02T14:48", formatter));
        lecture.setGroups(List.of(new Group(1, "name", Year.FIRST)));

        return new LectureGetDTO.Builder().lecture(lecture)
                                          .groups(lecture.getGroups())
                                          .allCourses(List.of(new Course(1, "name", "description")))
                                          .allAudiences(List.of(new Audience(1, 20, 20)))
                                          .allTeachers(List.of(new Teacher(1, "fName1", "lName1")))
                                          .allGroupsExceptAdded(new ArrayList<>())
                                          .build();
    }

    private LectureGetDTO retrieveGetDTONew() {
        return new LectureGetDTO.Builder()
                .allCourses(List.of(new Course(1, "name", "description")))
                .allAudiences(List.of(new Audience(1, 20, 20)))
                .allTeachers(List.of(new Teacher(1, "fName1", "lName1")))
                .build();
    }

    private List<Lecture> retrieveTestLectures() {
        List<Group> groups = List.of(new Group(1, "name", Year.FIRST));
        Lecture lecture1 = new Lecture();
        lecture1.setId(1);
        lecture1.setGroups(groups);
        Lecture lecture2 = new Lecture();
        lecture1.setId(2);
        lecture2.setGroups(groups);
        Lecture lecture3 = new Lecture();
        lecture1.setId(3);
        lecture3.setGroups(groups);
        return List.of(lecture1, lecture2, lecture3);
    }

    private List<LectureGetDTO> retrieveTestLectureDTOs(List<Lecture> lectures) {
        return lectures.stream()
                .map(s -> new LectureGetDTO.Builder().lecture(s).groups(s.getGroups())
                        .build()).collect(Collectors.toList());
    }
}
