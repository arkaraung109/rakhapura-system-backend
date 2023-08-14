package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.util.UUID;

public class PublicExamResultDto implements Serializable {

    private UUID id;

    private int serialNo;

    private StudentClassDto studentClass;

    public PublicExamResultDto() {
    }

    public PublicExamResultDto(UUID id, int serialNo, StudentClassDto studentClass) {
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

    public StudentClassDto getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClassDto studentClass) {
        this.studentClass = studentClass;
    }

}
