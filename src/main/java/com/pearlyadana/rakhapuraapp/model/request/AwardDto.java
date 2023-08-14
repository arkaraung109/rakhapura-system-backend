package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class AwardDto implements Serializable {

    private Long id;

    private String award;

    private String description;

    private String eventDate;

    private StudentDto student;

    public AwardDto() {
    }

    public AwardDto(Long id, String award, String description, String eventDate, StudentDto student) {
        this.id = id;
        this.award = award;
        this.description = description;
        this.eventDate = eventDate;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

}
