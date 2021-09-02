package com.foxminded.koren.university.controller;

import com.foxminded.koren.university.controller.dto.LectureGetDTO;
import com.foxminded.koren.university.controller.dto.LecturePostDTO;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.entity.*;
import com.foxminded.koren.university.service.*;
import com.foxminded.koren.university.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<LectureGetDTO> dtos = generateLectureDTO(lectures);
        model.addAttribute("dtos", dtos);
        LOG.trace("Retrieving all lectures : success");
        return "lectures/index";
    }

    private List<LectureGetDTO> generateLectureDTO(List<Lecture> lectures) {
        List<LectureGetDTO> dtos = new ArrayList<>();
        for(Lecture lecture : lectures) {
            List<Group> groups = groupService.getGroupsByLectureId(lecture.getId());
            dtos.add(new LectureGetDTO.Builder().lecture(lecture).groups(groups).build());
        }
        return  dtos;
    }

    @GetMapping("/new")
    public String newLecture(Model model, @ModelAttribute("formDTO") LecturePostDTO formDTO) {
        LOG.trace("Request for form to create new Lecture");
        LectureGetDTO dto = new LectureGetDTO.Builder()
                .allCourses(courseService.getAll())
                .allAudiences(audienceService.getAll())
                .allTeachers(teacherService.getAll())
                .build();
        model.addAttribute("dto", dto);
        return "lectures/new";
    }

    @PostMapping("new-create")
    public String create(@ModelAttribute("formDTO") LecturePostDTO formDTO) {
        LOG.trace("Creating new lecture");
        Lecture lecture = new Lecture(new Audience(formDTO.getAudienceId()),
                new Teacher(formDTO.getTeacherId()),
                new Course(formDTO.getCourseId()),
                formDTO.getStartTime(),
                formDTO.getEndTime());
        try {
            lectureService.createNew(lecture);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Creating new lecture : success. lecture id = {}", lecture.getId());
        return String.format("redirect:/lectures/%s/edit", lecture.getId());
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        LOG.trace("Request for form to update lecture id = {}", id);
        LectureGetDTO dto = new LectureGetDTO.Builder()
                .lecture(lectureService.getById(id))
                .groups(groupService.getGroupsByLectureId(id))
                .allCourses(courseService.getAll())
                .allAudiences(audienceService.getAll())
                .allTeachers(teacherService.getAll())
                .allGroupsExceptAdded(groupService.getAllExceptAddedToLecture(id))
                .build();
        model.addAttribute("dto", dto);
        LecturePostDTO formDTO = new LecturePostDTO(dto.getLecture().getCourse().getId(),
                                                    dto.getLecture().getAudience().getId(),
                                                    dto.getLecture().getTeacher().getId(),
                                                    dto.getLecture().getStartTime(),
                                                    dto.getLecture().getEndTime());
        model.addAttribute("formDTO", formDTO);
        LOG.trace("Request for form to update lecture id = {} : success", id);
        return "lectures/edit";
    }

    @PostMapping("/{id}/edit-update")
    public String update(@ModelAttribute("formDTO") LecturePostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Updating lecture id = {}", id);
        Lecture lecture = new Lecture(id,
                                      new Audience(formDTO.getAudienceId()),
                                      new Teacher(formDTO.getTeacherId()),
                                      new Course(formDTO.getCourseId()),
                                      formDTO.getStartTime(),
                                      formDTO.getEndTime());
        try {
            lectureService.update(lecture);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        LOG.trace("Updating lecture id = {} : success", lecture.getId());
        return "redirect:/lectures";
    }

    @PostMapping("/{id}-remove-group")
    public String removeGroup(@ModelAttribute("formDTO") LecturePostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Removing group id = {} from lecture id = {}", id, formDTO.getGroupId());
        try {
            lectureService.removeGroup(id, formDTO.getGroupId());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Removing group id = {} from lecture id = {} : success", id, formDTO.getGroupId());
        return String.format("redirect:/lectures/%s/edit", id);
    }

    @PostMapping("/{id}-add-group")
    public String addGroup(@ModelAttribute("formDTO") LecturePostDTO formDTO, @PathVariable("id") int id) {
        LOG.trace("Adding group id = {} to lecture id = {}", id, formDTO.getGroupId());
        try {
            lectureService.addGroup(id, formDTO.getGroupId());
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Adding group id = {} to lecture id = {} : success", id, formDTO.getGroupId());
        return String.format("redirect:/lectures/%s/edit", id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        LOG.trace("Deleting lecture id = {}", id);
        try {
            lectureService.deleteById(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
        LOG.trace("Deleting lecture id = {} : success", id);
        return "redirect:/lectures";
    }
}