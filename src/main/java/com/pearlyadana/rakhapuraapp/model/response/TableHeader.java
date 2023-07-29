package com.pearlyadana.rakhapuraapp.model.response;

import java.util.List;

public class TableHeader {

    private String academicYear;

    private String examTitle;

    private String grade;

    private List<CustomExam> customExamList;

    private List<String> examSubjectList;

    private List<String> givenMarkList;

    public TableHeader() {
    }

    public TableHeader(String academicYear, String examTitle, String grade, List<CustomExam> customExamList, List<String> examSubjectList, List<String> givenMarkList) {
        this.academicYear = academicYear;
        this.examTitle = examTitle;
        this.grade = grade;
        this.customExamList = customExamList;
        this.examSubjectList = examSubjectList;
        this.givenMarkList = givenMarkList;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<CustomExam> getCustomExamList() {
        return customExamList;
    }

    public void setCustomExamList(List<CustomExam> customExamList) {
        this.customExamList = customExamList;
    }

    public List<String> getExamSubjectList() {
        return examSubjectList;
    }

    public void setExamSubjectList(List<String> examSubjectList) {
        this.examSubjectList = examSubjectList;
    }

    public List<String> getGivenMarkList() {
        return givenMarkList;
    }

    public void setGivenMarkList(List<String> givenMarkList) {
        this.givenMarkList = givenMarkList;
    }

}
