package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class StudentExamModerateDto implements Serializable {

    private UUID id;

    private int mark;

    private LocalDateTime createdTimestamp;

    private ExamSubjectDto examSubject;

    private AttendanceDto attendance;

    public StudentExamModerateDto() {
    }

    public StudentExamModerateDto(UUID id, int mark, LocalDateTime createdTimestamp, ExamSubjectDto examSubject, AttendanceDto attendance) {
        this.id = id;
        this.mark = mark;
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
