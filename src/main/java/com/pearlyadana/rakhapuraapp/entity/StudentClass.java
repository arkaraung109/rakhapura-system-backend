package com.pearlyadana.rakhapuraapp.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_class")
public class StudentClass {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(name = "reg_no", length = 30)
    private String regNo;

    @Column(name = "reg_seq_no")
    private int regSeqNo;

    @Column(name = "arrival")
    private boolean arrival;

    @Column(name = "createdTimestamp")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_title_id")
    private ExamTitle examTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Class studentClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public StudentClass() {
    }

    public StudentClass(UUID id, String regNo, int regSeqNo, boolean arrival, LocalDateTime createdTimestamp, ExamTitle examTitle, Class studentClass, Hostel hostel, Student student) {
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

    public ExamTitle getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(ExamTitle examTitle) {
        this.examTitle = examTitle;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
