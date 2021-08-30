package com.foxminded.koren.university.controller.dto;

public class StudentPostDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer groupId;
    public StudentPostDTO() {

    }

    public StudentPostDTO(Integer id) {
        this.id = id;
    }

    public StudentPostDTO(Integer studentId, String firstName, String lastName, Integer groupId) {
        this.id = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
