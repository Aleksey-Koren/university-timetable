package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Lecture;

import java.util.List;
import java.util.Objects;

public class LectureDTO {

    private Lecture lecture;
    private List<Group> groups;

    public LectureDTO(Lecture lecture, List<Group> groups) {
        this.lecture = lecture;
        this.groups = groups;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureDTO that = (LectureDTO) o;
        return Objects.equals(lecture, that.lecture) && Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecture, groups);
    }
}
