package com.pearlyadana.rakhapuraapp.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "student_exam")
public class StudentExam {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(name = "mark")
    private int mark;

    @Column(name = "pass")
    private boolean pass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_subject_id")
    private ExamSubject examSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    public StudentExam() {
    }

    public StudentExam(UUID id, int mark, boolean pass, ExamSubject examSubject, Attendance attendance) {
        this.id = id;
        this.mark = mark;
        this.pass = pass;
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

    public ExamSubject getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(ExamSubject examSubject) {
        this.examSubject = examSubject;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

}
