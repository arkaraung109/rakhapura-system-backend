package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class ExamSubjectDto implements Serializable {

    private Long id;

    private int passMark;

    private int markPercentage;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    private ExamDto exam;

    private SubjectDto subject;

    public ExamSubjectDto() {
    }

    public ExamSubjectDto(Long id, int passMark, int markPercentage, boolean authorizedStatus, Long authorizedUserId, ExamDto exam, SubjectDto subject) {
        this.id = id;
        this.passMark = passMark;
        this.markPercentage = markPercentage;
        this.authorizedStatus = authorizedStatus;
        this.authorizedUserId = authorizedUserId;
        this.exam = exam;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

}
