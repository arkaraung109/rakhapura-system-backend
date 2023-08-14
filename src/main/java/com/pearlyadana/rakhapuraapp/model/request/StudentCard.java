package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class StudentCard implements Serializable {

    private Long academicYearId;

    private Long examTitleId;

    private Long gradeId;

    private List<UUID> idList;

    private String cardDate;

    private int examHoldingTimes;

    public StudentCard() {
    }

    public StudentCard(Long academicYearId, Long examTitleId, Long gradeId, List<UUID> idList, String cardDate, int examHoldingTimes) {
        this.academicYearId = academicYearId;
        this.examTitleId = examTitleId;
        this.gradeId = gradeId;
        this.idList = idList;
        this.cardDate = cardDate;
        this.examHoldingTimes = examHoldingTimes;
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

    public List<UUID> getIdList() {
        return idList;
    }

    public void setIdList(List<UUID> idList) {
        this.idList = idList;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public int getExamHoldingTimes() {
        return examHoldingTimes;
    }

    public void setExamHoldingTimes(int examHoldingTimes) {
        this.examHoldingTimes = examHoldingTimes;
    }

}
