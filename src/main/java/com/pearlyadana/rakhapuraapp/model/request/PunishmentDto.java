package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class PunishmentDto implements Serializable {

    private Long id;

    private String punishment;

    private String description;

    private String eventDate;

    private String startDate;

    private String endDate;

    private StudentDto student;

    public PunishmentDto() {
    }

    public PunishmentDto(Long id, String punishment, String description, String eventDate, String startDate, String endDate, StudentDto student) {
        this.id = id;
        this.punishment = punishment;
        this.description = description;
        this.eventDate = eventDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

}
