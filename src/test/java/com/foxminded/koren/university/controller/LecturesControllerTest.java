package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.controller.dto.LectureGetDTO;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.service.GroupService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
class LecturesControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private LectureService mockedLectureService;
    @Mock
    @Autowired
    private GroupService mockedGroupService;

    @BeforeEach
    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LecturesController(mockedLectureService, mockedGroupService)).build();
    }

    @Test
    void index_shouldAddExpectedListIntoModelAndSendItToRightView() throws Exception {
        List<Lecture> testLectures = retrieveTestLectures();
        when(mockedLectureService.getAll()).thenReturn(testLectures);
        when(mockedGroupService.getGroupsByLectureId(any())).thenReturn(List.of(new Group(), new Group(), new Group()));
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
        InOrder inOrder = inOrder(mockedLectureService,mockedGroupService);
        mockMvc.perform(get("/lectures"));
        inOrder.verify(mockedLectureService, times(1)).getAll();
        inOrder.verify(mockedGroupService,times(retrieveTestLectures().size())).getGroupsByLectureId(any());
        inOrder.verifyNoMoreInteractions();
    }


    private List<Lecture> retrieveTestLectures() {
        Lecture lecture1 = new Lecture();
        lecture1.setId(1);
        Lecture lecture2 = new Lecture();
        lecture1.setId(2);
        Lecture lecture3 = new Lecture();
        lecture1.setId(3);
        return List.of(lecture1, lecture2, lecture3);
    }

    private List<LectureGetDTO> retrieveTestLectureDTOs(List<Lecture> lectures) {
        List<LectureGetDTO> dtos = lectures.stream()
                .map(s -> new LectureGetDTO.Builder().lecture(s).groups(List.of(new Group(), new Group(), new Group()))
                .build()).collect(Collectors.toList());
        return dtos;
    }

}
