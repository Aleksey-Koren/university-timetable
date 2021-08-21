package com.foxminded.koren.university.controller.dto;

public class LectureFormDTO {

    private Integer courseId;
    private Integer audienceId;
    private Integer teacherId;
    private Integer newGroupId;


    public LectureFormDTO() {

    }

    public LectureFormDTO(Integer courseId, Integer audienceId, Integer teacherId) {
        this.courseId = courseId;
        this.audienceId = audienceId;
        this.teacherId = teacherId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getAudienceId() {
        return audienceId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public Integer getNewGroupId() {
        return newGroupId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setAudienceId(Integer audienceId) {
        this.audienceId = audienceId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public void setNewGroupId(Integer newGroupId) {
        this.newGroupId = newGroupId;
    }
}