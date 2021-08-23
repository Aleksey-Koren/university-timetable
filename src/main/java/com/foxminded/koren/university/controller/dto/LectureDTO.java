package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.*;

import java.util.List;
import java.util.Objects;

public class LectureDTO {

    private Lecture lecture;
    private List<Group> groups;
    private List<Course> allCourses;
    private List<Group> allGroups;
    private List<Audience> allAudiences;
    private List<Teacher> allTeachers;
    private List<Group> allGroupsExceptAdded;

    public LectureDTO() {

    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Course> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<Course> allCourses) {
        this.allCourses = allCourses;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(List<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public List<Audience> getAllAudiences() {
        return allAudiences;
    }

    public void setAllAudiences(List<Audience> allAudiences) {
        this.allAudiences = allAudiences;
    }

    public List<Teacher> getAllTeachers() {
        return allTeachers;
    }

    public void setAllTeachers(List<Teacher> allTeachers) {
        this.allTeachers = allTeachers;
    }

    public List<Group> getAllGroupsExceptAdded() {
        return allGroupsExceptAdded;
    }

    public void setAllGroupsExceptAdded(List<Group> allGroupsExceptAdded) {
        this.allGroupsExceptAdded = allGroupsExceptAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureDTO that = (LectureDTO) o;
        return Objects.equals(lecture, that.lecture) && Objects.equals(groups, that.groups) && Objects.equals(allCourses, that.allCourses) && Objects.equals(allGroups, that.allGroups) && Objects.equals(allAudiences, that.allAudiences) && Objects.equals(allTeachers, that.allTeachers) && Objects.equals(allGroupsExceptAdded, that.allGroupsExceptAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecture, groups, allCourses, allGroups, allAudiences, allTeachers, allGroupsExceptAdded);
    }

    public static class Builder {

        private LectureDTO newDTO;

        public Builder() {
            newDTO = new LectureDTO();
        }

        public Builder lecture(Lecture lecture) {
            newDTO.lecture = lecture;
            return this;
        }

        public Builder groups(List<Group> groups) {
            newDTO.groups = groups;
            return this;
        }

        public Builder allCourses(List<Course> allCourses) {
            newDTO.allCourses = allCourses;
            return this;
        }

        public Builder allGroups(List<Group> allGroups) {
            newDTO.allGroups = allGroups;
            return this;
        }

        public Builder allAudiences(List<Audience> allAudiences) {
            newDTO.allAudiences = allAudiences;
            return this;
        }

        public Builder allTeachers(List<Teacher> allTeachers) {
            newDTO.allTeachers = allTeachers;
            return this;
        }

        public Builder allGroupsExceptAdded(List<Group> allGroupsExceptAdded) {
            newDTO.allGroupsExceptAdded = allGroupsExceptAdded;
            return this;
        }

        public LectureDTO build() {
            return newDTO;
        }
    }
}
