package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class Certificate implements Serializable {

    private Long academicYearId;

    private Long examTitleId;

    private Long gradeId;

    private String tharthanarYear;

    private String kawzarYear;

    private String chrisYear;

    private String examDate;

    private int examHoldingTimes;

    private String releasedDate;

    public Certificate() {
    }

    public Certificate(Long academicYearId, Long examTitleId, Long gradeId, String tharthanarYear, String kawzarYear, String chrisYear, String examDate, int examHoldingTimes, String releasedDate) {
        this.academicYearId = academicYearId;
        this.examTitleId = examTitleId;
        this.gradeId = gradeId;
        this.tharthanarYear = tharthanarYear;
        this.kawzarYear = kawzarYear;
        this.chrisYear = chrisYear;
        this.examDate = examDate;
        this.examHoldingTimes = examHoldingTimes;
        this.releasedDate = releasedDate;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Long getExamTitleId() {
        return examTitleId;
    }

    public void setExamTitleId(Long examTitleId) {
        this.examTitleId = examTitleId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getTharthanarYear() {
        return tharthanarYear;
    }

    public void setTharthanarYear(String tharthanarYear) {
        this.tharthanarYear = tharthanarYear;
    }

    public String getKawzarYear() {
        return kawzarYear;
    }

    public void setKawzarYear(String kawzarYear) {
        this.kawzarYear = kawzarYear;
    }

    public String getChrisYear() {
        return chrisYear;
    }

    public void setChrisYear(String chrisYear) {
        this.chrisYear = chrisYear;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public int getExamHoldingTimes() {
        return examHoldingTimes;
    }

    public void setExamHoldingTimes(int examHoldingTimes) {
        this.examHoldingTimes = examHoldingTimes;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

}
