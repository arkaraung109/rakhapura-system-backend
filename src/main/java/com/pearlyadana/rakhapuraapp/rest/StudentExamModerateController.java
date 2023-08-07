package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.*;
import com.pearlyadana.rakhapuraapp.model.response.*;
import com.pearlyadana.rakhapuraapp.service.*;
import com.pearlyadana.rakhapuraapp.util.StudentExamModerateExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/student-exam-moderates", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentExamModerateController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamSubjectService examSubjectService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentExamService studentExamService;

    @Autowired
    private StudentExamModerateService studentExamModerateService;

    @GetMapping("/filterOne")
    public ResponseEntity<StudentExamModerateDto> findByExamSubjectAndAttendance(@RequestParam Long examSubjectId, @RequestParam UUID attendanceId) {
        return new ResponseEntity<>(this.studentExamModerateService.findByExamSubjectAndAttendance(examSubjectId, attendanceId), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_MARK_ENTRY)
    public ResponseEntity<CustomHttpResponse> moderate(@RequestBody StudentExamDto body) {
        StudentExamDto dto = this.studentExamService.findById(body.getId());
        int passMark = dto.getExamSubject().getPassMark();
        StudentExamModerateDto studentExamModerateDto = new StudentExamModerateDto();
        studentExamModerateDto.setMark(dto.getMark());
        studentExamModerateDto.setExamSubject(dto.getExamSubject());
        studentExamModerateDto.setAttendance(dto.getAttendance());
        dto.setMark(passMark);
        dto.setPass(true);
        StudentExamModerateDto sem = this.studentExamModerateService.findByExamSubjectAndAttendance(dto.getExamSubject().getId(), dto.getAttendance().getId());
        if(sem != null) {
            studentExamModerateDto.setId(sem.getId());
        }
        if(this.studentExamModerateService.save(studentExamModerateDto) != null) {
            if(this.studentExamService.save(dto) != null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private TableHeader setTableHeader(Long academicYearId, Long examTitleId, Long gradeId, String keyword) {
        TableHeader tableHeader = new TableHeader();
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId);
        List<CustomExam> customExamList = new ArrayList<>();
        List<String> examSubjectList = new ArrayList<>();
        List<String> givenMarkList = new ArrayList<>();
        int totalPassMark = 0;
        int totalMarkPercentage = 0;
        for(ExamDto examDto : examDtoList) {
            List<ExamSubjectDto> examSubjectDtoList = this.examSubjectService.findAllAuthorizedByExam(examDto.getId());
            for(ExamSubjectDto examSubjectDto : examSubjectDtoList) {
                examSubjectList.add(examSubjectDto.getSubject().getName());
                givenMarkList.add(examSubjectDto.getPassMark() + "/" + examSubjectDto.getMarkPercentage());
            }
            examSubjectList.add("စုစုပေါင်း");
            givenMarkList.add(examDto.getPassMark() + "/" + examDto.getMarkPercentage());

            totalPassMark += examDto.getPassMark();
            totalMarkPercentage += examDto.getMarkPercentage();

            CustomExam customExam = new CustomExam();
            customExam.setExam(examDto);
            customExam.setExamSubjectCount(examSubjectDtoList.size());
            customExamList.add(customExam);
        }
        givenMarkList.add(totalPassMark + "/" + totalMarkPercentage);

        tableHeader.setAcademicYear(customExamList.get(0).getExam().getAcademicYear().getName());
        tableHeader.setExamTitle(customExamList.get(0).getExam().getExamTitle().getName());
        tableHeader.setGrade(customExamList.get(0).getExam().getSubjectType().getGrade().getName());
        tableHeader.setCustomExamList(customExamList);
        tableHeader.setExamSubjectList(examSubjectList);
        tableHeader.setGivenMarkList(givenMarkList);
        return tableHeader;
    }

    @GetMapping("/result/search")
    public CustomPaginationResponse<ResultResponse> findEachPageBySearching(@RequestParam int page, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam String keyword) {
        CustomPaginationResponse<ResultResponse> customPaginationResponse = this.attendanceService.findEachModerationPageBySearching(page, academicYearId, examTitleId, gradeId, keyword);
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId);
        int overAllPassMark = 0;
        for(ExamDto examDto : examDtoList) {
            overAllPassMark += examDto.getPassMark();
        }
        for(ResultResponse element : customPaginationResponse.getElements()) {
            List<AttendanceDto> attendanceDtoList = this.attendanceService.findByStudentClassId(element.getAttendance().getStudentClass().getId());
            List<ExamResult> examResultList = new ArrayList<>();
            int overAllMark = 0;
            boolean hasModeration = false;
            for(AttendanceDto attendanceDto : attendanceDtoList) {
                List<ExamSubjectDto> examSubjectDtoList = this.examSubjectService.findAllAuthorizedByExam(attendanceDto.getExam().getId());
                int result = this.studentExamService.findResult(attendanceDto.getId());
                Integer totalMark = this.studentExamService.findTotalMark(attendanceDto.getId());
                ExamResult examResult = new ExamResult();
                List<SubjectResult> subjectResultList = new ArrayList<>();
                int examMark = 0;

                List<StudentExamDto> studentExamDtoList = this.studentExamService.findAllByAttendance(attendanceDto.getId());
                if(studentExamDtoList.isEmpty()) {
                    for(int i = 0; i < examSubjectDtoList.size(); i++) {
                        SubjectResult subjectResult = new SubjectResult();
                        subjectResult.setEmpty();
                        subjectResultList.add(subjectResult);
                    }
                    examResult.setSubjectResultList(subjectResultList);
                    examResult.setEmpty();
                } else {
                    for(StudentExamDto studentExamDto : studentExamDtoList) {
                        StudentExamModerateDto studentExamModerateDto = this.studentExamModerateService.findByExamSubjectAndAttendance(studentExamDto.getExamSubject().getId(), studentExamDto.getAttendance().getId());
                        SubjectResult subjectResult = new SubjectResult();
                        subjectResult.setMark(String.valueOf(studentExamDto.getMark()));
                        if(studentExamModerateDto != null) {
                            hasModeration = true;
                            subjectResult.setStatus("moderation");
                        } else {
                            if(studentExamDto.isPass()) {
                                subjectResult.setStatus("pass");
                            } else {
                                subjectResult.setStatus("fail");
                            }
                        }
                        examMark += studentExamDto.getMark();
                        subjectResultList.add(subjectResult);
                    }
                    examResult.setSubjectResultList(subjectResultList);
                    examResult.setMark(String.valueOf(examMark));
                    if(totalMark >= attendanceDto.getExam().getPassMark() && result == 1) {
                        examResult.setStatus("pass");
                    } else {
                        examResult.setStatus("fail");
                    }
                }
                examResultList.add(examResult);
                overAllMark += examMark;
            }
            element.setExamResultList(examResultList);
            OverAllMark overAllMarkObject = new OverAllMark();
            overAllMarkObject.setMark(String.valueOf(overAllMark));
            if(overAllMark < overAllPassMark) {
                overAllMarkObject.setStatus("fail");
                element.setStatus("fail");
            } else {
                overAllMarkObject.setStatus("pass");
                element.setStatus("pass");
            }
            if(hasModeration) {
                element.setStatus("moderation");
            }
            element.setOverAllMark(overAllMarkObject);
        }

        List<ResultResponse> resultResponseList = this.attendanceService.findModerationBySearching(academicYearId, examTitleId, gradeId, keyword);
        int totalAnswered = resultResponseList.size();
        int totalPassed = 0;
        int totalModerated = 0;
        int totalFailed = 0;
        for(ResultResponse element : resultResponseList) {
            List<AttendanceDto> attendanceDtoList = this.attendanceService.findByStudentClassId(element.getAttendance().getStudentClass().getId());
            int overAllMark = 0;
            boolean hasModeration = false;
            for(AttendanceDto attendanceDto : attendanceDtoList) {
                int examMark = 0;

                List<StudentExamDto> studentExamDtoList = this.studentExamService.findAllByAttendance(attendanceDto.getId());
                if(!studentExamDtoList.isEmpty()) {
                    for(StudentExamDto studentExamDto : studentExamDtoList) {
                        StudentExamModerateDto studentExamModerateDto = this.studentExamModerateService.findByExamSubjectAndAttendance(studentExamDto.getExamSubject().getId(), studentExamDto.getAttendance().getId());
                        if(studentExamModerateDto != null) {
                            hasModeration = true;
                        }
                        examMark += studentExamDto.getMark();
                    }
                }
                overAllMark += examMark;
            }
            if(overAllMark < overAllPassMark) {
                totalFailed++;
            } else {
                totalPassed++;
            }
            if(hasModeration) {
                totalModerated++;
            }
        }
        TableHeader tableHeader = this.setTableHeader(academicYearId, examTitleId, gradeId, keyword);
        customPaginationResponse.setTotalAnswered(totalAnswered);
        customPaginationResponse.setTotalPassed(totalPassed);
        customPaginationResponse.setTotalModerated(totalModerated);
        customPaginationResponse.setTotalFailed(totalFailed);
        customPaginationResponse.setTableHeader(tableHeader);
        return customPaginationResponse;
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam String keyword, HttpServletResponse response) throws IOException {
        CustomPaginationResponse<ResultResponse> customPaginationResponse = new CustomPaginationResponse<>();
        List<ResultResponse> resultResponseList = this.attendanceService.findModerationBySearching(academicYearId, examTitleId, gradeId, keyword);
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId);
        int overAllPassMark = 0;
        for(ExamDto examDto : examDtoList) {
            overAllPassMark += examDto.getPassMark();
        }
        int totalAnswered = resultResponseList.size();
        int totalPassed = 0;
        int totalModerated = 0;
        int totalFailed = 0;
        for(ResultResponse element : resultResponseList) {
            List<AttendanceDto> attendanceDtoList = this.attendanceService.findByStudentClassId(element.getAttendance().getStudentClass().getId());
            List<ExamResult> examResultList = new ArrayList<>();
            int overAllMark = 0;
            boolean hasModeration = false;
            for(AttendanceDto attendanceDto : attendanceDtoList) {
                List<ExamSubjectDto> examSubjectDtoList = this.examSubjectService.findAllAuthorizedByExam(attendanceDto.getExam().getId());
                int result = this.studentExamService.findResult(attendanceDto.getId());
                Integer totalMark = this.studentExamService.findTotalMark(attendanceDto.getId());
                ExamResult examResult = new ExamResult();
                List<SubjectResult> subjectResultList = new ArrayList<>();
                int examMark = 0;

                List<StudentExamDto> studentExamDtoList = this.studentExamService.findAllByAttendance(attendanceDto.getId());
                if(studentExamDtoList.isEmpty()) {
                    for(int i = 0; i < examSubjectDtoList.size(); i++) {
                        SubjectResult subjectResult = new SubjectResult();
                        subjectResult.setEmpty();
                        subjectResultList.add(subjectResult);
                    }
                    examResult.setSubjectResultList(subjectResultList);
                    examResult.setEmpty();
                } else {
                    for(StudentExamDto studentExamDto : studentExamDtoList) {
                        StudentExamModerateDto studentExamModerateDto = this.studentExamModerateService.findByExamSubjectAndAttendance(studentExamDto.getExamSubject().getId(), studentExamDto.getAttendance().getId());
                        SubjectResult subjectResult = new SubjectResult();
                        subjectResult.setMark(String.valueOf(studentExamDto.getMark()));
                        if(studentExamModerateDto != null) {
                            hasModeration = true;
                            subjectResult.setStatus("moderation");
                        }
                        else {
                            if(studentExamDto.isPass()) {
                                subjectResult.setStatus("pass");
                            } else {
                                subjectResult.setStatus("fail");
                            }
                        }
                        examMark += studentExamDto.getMark();
                        subjectResultList.add(subjectResult);
                    }
                    examResult.setSubjectResultList(subjectResultList);
                    examResult.setMark(String.valueOf(examMark));
                    if(totalMark >= attendanceDto.getExam().getPassMark() && result == 1) {
                        examResult.setStatus("pass");
                    } else {
                        examResult.setStatus("fail");
                    }
                }
                examResultList.add(examResult);
                overAllMark += examMark;
            }
            element.setExamResultList(examResultList);
            OverAllMark overAllMarkObject = new OverAllMark();
            overAllMarkObject.setMark(String.valueOf(overAllMark));
            if(overAllMark < overAllPassMark) {
                overAllMarkObject.setStatus("fail");
                element.setStatus("fail");
                totalFailed++;
            } else {
                overAllMarkObject.setStatus("pass");
                element.setStatus("pass");
                totalPassed++;
            }
            if(hasModeration) {
                element.setStatus("moderate");
                totalModerated++;
            }
            element.setOverAllMark(overAllMarkObject);
        }
        TableHeader tableHeader = this.setTableHeader(academicYearId, examTitleId, gradeId, keyword);
        customPaginationResponse.setElements(resultResponseList);
        customPaginationResponse.setTotalAnswered(totalAnswered);
        customPaginationResponse.setTotalPassed(totalPassed);
        customPaginationResponse.setTotalModerated(totalModerated);
        customPaginationResponse.setTotalFailed(totalFailed);
        customPaginationResponse.setTableHeader(tableHeader);

        response.setContentType("application/octet-stream");
        StudentExamModerateExcelGenerator generator = new StudentExamModerateExcelGenerator(customPaginationResponse);
        generator.export(response);
    }

}
