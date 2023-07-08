package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class ExamDto implements Serializable {

    private Long id;

    private String examDate;

    private String time;

    private int passMark;

    private int markPercentage;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    private AcademicYearDto academicYear;

    private ExamTitleDto examTitle;

    private SubjectTypeDto subjectType;

    public ExamDto() {
    }

    public ExamDto(Long id, String examDate, String time, int passMark, int markPercentage, boolean authorizedStatus, Long authorizedUserId, AcademicYearDto academicYear, ExamTitleDto examTitle, SubjectTypeDto subjectType) {
        this.id = id;
        this.examDate = examDate;
        this.time = time;
        this.passMark = passMark;
        this.markPercentage = markPercentage;
        this.authorizedStatus = authorizedStatus;
        this.authorizedUserId = authorizedUserId;
        this.academicYear = academicYear;
        this.examTitle = examTitle;
        this.subjectType = subjectType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPassMark() {
        return passMark;
    }

    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

    public int getMarkPercentage() {
        return markPercentage;
    }

    public void setMarkPercentage(int markPercentage) {
        this.markPercentage = markPercentage;
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

    public ExamTitleDto getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(ExamTitleDto examTitle) {
        this.examTitle = examTitle;
    }

    public SubjectTypeDto getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectTypeDto subjectType) {
        this.subjectType = subjectType;
    }

}
