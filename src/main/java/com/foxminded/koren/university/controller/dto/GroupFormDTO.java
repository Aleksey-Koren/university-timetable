package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Year;

public class GroupFormDTO {

    private String groupName;
    private Year year;
    private int studentToRemoveId;

    public GroupFormDTO() {
    }

    public GroupFormDTO(String groupName, Year year) {
        this.groupName = groupName;
        this.year = year;
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

    public int getStudentToRemoveId() {
        return studentToRemoveId;
    }

    public void setStudentToRemoveId(int studentToRemoveId) {
        this.studentToRemoveId = studentToRemoveId;
    }
}
