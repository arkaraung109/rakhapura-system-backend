package com.pearlyadana.rakhapuraapp.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "public_exam_result")
public class PublicExamResult {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(name = "serial_no")
    private int serialNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_class_id")
    private StudentClass studentClass;

    public PublicExamResult() {
    }

    public PublicExamResult(UUID id, int serialNo, StudentClass studentClass) {
        this.id = id;
        this.serialNo = serialNo;
        this.studentClass = studentClass;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

}
