package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class ClassDto implements Serializable {

    private Long id;

    private String name;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    private AcademicYearDto academicYear;

    private GradeDto grade;

    public ClassDto() {
    }

    public ClassDto(Long id, String name, boolean authorizedStatus, Long authorizedUserId, AcademicYearDto academicYear, GradeDto grade) {
        this.id = id;
        this.name = name;
        this.authorizedStatus = authorizedStatus;
        this.authorizedUserId = authorizedUserId;
        this.academicYear = academicYear;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuthorizedStatus() {
        return authorizedStatus;
    }

    public void setAuthorizedStatus(boolean authorizedStatus) {
        this.authorizedStatus = authorizedStatus;
    }

    public Long getAuthorizedUserId() {
        return authorizedUserId;
    }

    public void setAuthorizedUserId(Long authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }

    public AcademicYearDto getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYearDto academicYear) {
        this.academicYear = academicYear;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

}
