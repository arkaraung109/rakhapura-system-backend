package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "exam_subject")
public class ExamSubject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pass_mark")
    private int passMark;

    @Column(name = "mark_percentage")
    private int markPercentage;

    @Column(name = "authorized_status")
    private boolean authorizedStatus;

    @Column(name = "authorized_user_id")
    private Long authorizedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public ExamSubject() {
    }

    public ExamSubject(Long id, int passMark, int markPercentage, boolean authorizedStatus, Long authorizedUserId, Exam exam, Subject subject) {
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

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
