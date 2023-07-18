package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class StudentExamDto implements Serializable {

    private UUID id;

    private int mark;

    private boolean pass;

    private LocalDateTime createdTimestamp;

    private ExamSubjectDto examSubject;

    private AttendanceDto attendance;

    public StudentExamDto() {
    }

    public StudentExamDto(UUID id, int mark, boolean pass, LocalDateTime createdTimestamp, ExamSubjectDto examSubject, AttendanceDto attendance) {
        this.id = id;
        this.mark = mark;
        this.pass = pass;
        this.createdTimestamp = createdTimestamp;
        this.examSubject = examSubject;
        this.attendance = attendance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public ExamSubjectDto getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(ExamSubjectDto examSubject) {
        this.examSubject = examSubject;
    }

    public AttendanceDto getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceDto attendance) {
        this.attendance = attendance;
    }

}
