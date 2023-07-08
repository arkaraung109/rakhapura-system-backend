package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_date", length = 30)
    private String examDate;

    @Column(name = "time", length = 20)
    private String time;

    @Column(name = "pass_mark")
    private int passMark;

    @Column(name = "mark_percentage")
    private int markPercentage;

    @Column(name = "authorized_status")
    private boolean authorizedStatus;

    @Column(name = "authorized_user_id")
    private Long authorizedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_title_id")
    private ExamTitle examTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_type_id")
    private SubjectType subjectType;

    public Exam() {
    }

    public Exam(Long id, String examDate, String time, int passMark, int markPercentage, boolean authorizedStatus, Long authorizedUserId, AcademicYear academicYear, ExamTitle examTitle, SubjectType subjectType) {
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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public ExamTitle getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(ExamTitle examTitle) {
        this.examTitle = examTitle;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

}
