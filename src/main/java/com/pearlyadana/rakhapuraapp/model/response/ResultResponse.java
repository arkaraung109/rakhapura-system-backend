package com.pearlyadana.rakhapuraapp.model.response;

import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;

import java.util.List;

public class ResultResponse {

    private AttendanceDto attendance;

    private List<ExamResult> examResultList;

    private OverAllMark overAllMark;

    private String status;

    private List<AttendanceDto> attendedExamList;

    public ResultResponse() {
    }

    public ResultResponse(AttendanceDto attendance, List<ExamResult> examResultList, OverAllMark overAllMark, String status, List<AttendanceDto> attendedExamList) {
        this.attendance = attendance;
        this.examResultList = examResultList;
        this.overAllMark = overAllMark;
        this.status = status;
        this.attendedExamList = attendedExamList;
    }

    public AttendanceDto getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceDto attendance) {
        this.attendance = attendance;
    }

    public List<ExamResult> getExamResultList() {
        return examResultList;
    }

    public void setExamResultList(List<ExamResult> examResultList) {
        this.examResultList = examResultList;
    }

    public OverAllMark getOverAllMark() {
        return overAllMark;
    }

    public void setOverAllMark(OverAllMark overAllMark) {
        this.overAllMark = overAllMark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttendanceDto> getAttendedExamList() {
        return attendedExamList;
    }

    public void setAttendedExamList(List<AttendanceDto> attendedExamList) {
        this.attendedExamList = attendedExamList;
    }

    public void setEmpty() {
        this.status = "-";
    }

}
