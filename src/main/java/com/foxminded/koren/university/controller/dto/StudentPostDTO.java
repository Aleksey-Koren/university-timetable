package com.foxminded.koren.university.controller.dto;

import java.util.Objects;

public class StudentPostDTO {

    private Integer studentId;
    private String firstName;
    private String lastName;
    private Integer groupId;

    public StudentPostDTO() {

    }

    public StudentPostDTO(Integer id) {
        this.studentId = id;
    }

    public StudentPostDTO(Integer studentId, Integer groupId, String firstName, String lastName ) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentPostDTO that = (StudentPostDTO) o;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, groupId);
    }

    @Override
    public String toString() {
        return "StudentPostDTO{" +
                "id=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
