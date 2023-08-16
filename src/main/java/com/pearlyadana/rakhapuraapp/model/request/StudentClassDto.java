package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class StudentClassDto implements Serializable {

    private UUID id;

    private String regNo;

    private int regSeqNo;

    private boolean arrival;

    private LocalDateTime createdTimestamp;

    private ExamTitleDto examTitle;

    private ClassDto studentClass;

    private HostelDto hostel;

    private StudentDto student;

    public StudentClassDto() {
    }

    public StudentClassDto(UUID id, String regNo, int regSeqNo, boolean arrival, LocalDateTime createdTimestamp, ExamTitleDto examTitle, ClassDto studentClass, HostelDto hostel, StudentDto student) {
        this.id = id;
        this.regNo = regNo;
        this.regSeqNo = regSeqNo;
        this.arrival = arrival;
        this.createdTimestamp = createdTimestamp;
        this.examTitle = examTitle;
        this.studentClass = studentClass;
        this.hostel = hostel;
        this.student = student;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getRegSeqNo() {
        return regSeqNo;
    }

    public void setRegSeqNo(int regSeqNo) {
        this.regSeqNo = regSeqNo;
    }

    public boolean isArrival() {
        return arrival;
    }

    public void setArrival(boolean arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public ExamTitleDto getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(ExamTitleDto examTitle) {
        this.examTitle = examTitle;
    }

    public ClassDto getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(ClassDto studentClass) {
        this.studentClass = studentClass;
    }

    public HostelDto getHostel() {
        return hostel;
    }

    public void setHostel(HostelDto hostel) {
        this.hostel = hostel;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

}
