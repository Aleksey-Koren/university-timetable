package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.dto.LectureDTO;
import com.foxminded.koren.university.controller.exceptions.NoEntitiesInDatabaseException;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/lectures")
public class LecturesController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LecturesController.class);

    private LectureService lectureService;
    private GroupService groupService;
    private CourseService courseService;
    private AudienceService audienceService;
    private TeacherService teacherService;

    public LecturesController(LectureService lectureService, GroupService groupService) {
        this.lectureService = lectureService;
        this.groupService = groupService;
    }

    @Autowired
    public LecturesController(LectureService lectureService,
                              GroupService groupService,
                              CourseService courseService,
                              AudienceService audienceService,
                              TeacherService teacherService) {
        this.lectureService = lectureService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.audienceService = audienceService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String index(Model model) {
        LOG.trace("Retrieving all lectures");
        List<Lecture> lectures = lectureService.getAll();
        List<LectureDTO> dtos = generateLectureDTO(lectures);
        model.addAttribute("dtos", dtos);
        LOG.trace("Retrieving all lectures : success");
        return "lectures/index";
    }

    private List<LectureDTO> generateLectureDTO(List<Lecture> lectures) {
        List<LectureDTO> dtos = new ArrayList<>();
        for(Lecture lecture : lectures) {
            List<Group> groups = groupService.getGroupsByLectureId(lecture.getId());
            dtos.add(new LectureDTO(lecture, groups));
        }
        return  dtos;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        LOG.trace("Request for form to update lecture id = {}", id);
        LectureDTO dto = new LectureDTO.Builder()
                .lecture(lectureService.getById(id))
                        .groups(groupService.getGroupsByLectureId(id))
                                .allGroups(groupService.getAll())
                                        .allCourses(courseService.getAll())
                                                .allAudiences(audienceService.getAll())
                                                        .allTeachers(teacherService.getAll())
                                                                .build();
        model.addAttribute("dto", dto);
        LOG.trace("Request for form to update lecture id = {} : success", id);
        return "lectures/edit";
    }
}