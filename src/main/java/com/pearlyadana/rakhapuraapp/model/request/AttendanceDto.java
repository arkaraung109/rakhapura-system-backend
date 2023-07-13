package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class AttendanceDto implements Serializable {

    private UUID id;

    private boolean present;

    private LocalDateTime createdTimestamp;

    private ExamDto exam;

    private StudentClassDto studentClass;

    public AttendanceDto() {
    }

    public AttendanceDto(UUID id, boolean present, LocalDateTime createdTimestamp, ExamDto exam, StudentClassDto studentClass) {
        this.id = id;
        this.present = present;
        this.createdTimestamp = createdTimestamp;
        this.exam = exam;
        this.studentClass = studentClass;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    public StudentClassDto getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClassDto studentClass) {
        this.studentClass = studentClass;
    }

}
