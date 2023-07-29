package com.pearlyadana.rakhapuraapp.model.response;

import java.util.List;

public class ExamResult {

    private List<SubjectResult> subjectResultList;

    private String mark;

    private String status;

    public ExamResult() {
    }

    public ExamResult(List<SubjectResult> subjectResultList, String mark, String status) {
        this.subjectResultList = subjectResultList;
        this.mark = mark;
        this.status = status;
    }

    public List<SubjectResult> getSubjectResultList() {
        return subjectResultList;
    }

    public void setSubjectResultList(List<SubjectResult> subjectResultList) {
        this.subjectResultList = subjectResultList;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmpty() {
        this.mark = "-";
        this.status = "-";
    }

}
