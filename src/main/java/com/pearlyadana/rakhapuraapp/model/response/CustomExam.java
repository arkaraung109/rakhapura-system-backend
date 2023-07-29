package com.pearlyadana.rakhapuraapp.model.response;

import com.pearlyadana.rakhapuraapp.model.request.ExamDto;

public class CustomExam {

    private ExamDto exam;

    private int examSubjectCount;

    public CustomExam() {
    }

    public CustomExam(ExamDto exam, int examSubjectCount) {
        this.exam = exam;
        this.examSubjectCount = examSubjectCount;
    }

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    public int getExamSubjectCount() {
        return examSubjectCount;
    }

    public void setExamSubjectCount(int examSubjectCount) {
        this.examSubjectCount = examSubjectCount;
    }

}
