package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Year;

import java.util.List;
import java.util.Objects;

public class GroupPostDTO {

    private Integer groupId;
    private String groupName;
    private Year year;
    private int studentId;
    private List<Integer> studentsIds;

    public GroupPostDTO() {

    }

    public GroupPostDTO(String groupName, Year year) {
        this.groupName = groupName;
        this.year = year;
    }

    public GroupPostDTO(Integer groupId, String groupName, Year year, int studentId, List<Integer> studentsIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.year = year;
        this.studentId = studentId;
        this.studentsIds = studentsIds;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getStudentsIds() {
        return studentsIds;
    }

    public void setStudentsIds(List<Integer> studentsIds) {
        this.studentsIds = studentsIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupPostDTO that = (GroupPostDTO) o;
        return studentId == that.studentId && Objects.equals(groupId, that.groupId) && Objects.equals(groupName, that.groupName) && year == that.year && Objects.equals(studentsIds, that.studentsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, year, studentId, studentsIds);
    }

    @Override
    public String toString() {
        return "GroupPostDTO{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", year=" + year +
                ", studentId=" + studentId +
                ", studentsIds=" + studentsIds +
                '}';
    }

    public static class Builder {

        private GroupPostDTO dto = new GroupPostDTO();

        public Builder groupId(int groupId) {
            dto.groupId = groupId;
            return this;
        }

        public Builder groupName(String groupName) {
            dto.groupName = groupName;
            return this;
        }

        public Builder year(Year year) {
            dto.year = year;
            return this;
        }

        public Builder studentId(int studentId) {
            dto.studentId = studentId;
            return this;
        }

        public Builder studentsIds(List<Integer> studentsIds) {
            dto.studentsIds = studentsIds;
            return this;
        }

        public GroupPostDTO build() {
            return dto;
        }
    }
}
