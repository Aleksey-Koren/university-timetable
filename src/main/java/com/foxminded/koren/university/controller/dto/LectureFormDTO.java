package com.foxminded.koren.university.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class LectureFormDTO {

    private Integer courseId;
    private Integer audienceId;
    private Integer teacherId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    private Integer groupId;


    public LectureFormDTO() {

    }

    public LectureFormDTO(Integer courseId,
                          Integer audienceId,
                          Integer teacherId,
                          LocalDateTime startTime,
                          LocalDateTime endTime) {
        this.courseId = courseId;
        this.audienceId = audienceId;
        this.teacherId = teacherId;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Integer getGroupId() {
        return groupId;
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

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}