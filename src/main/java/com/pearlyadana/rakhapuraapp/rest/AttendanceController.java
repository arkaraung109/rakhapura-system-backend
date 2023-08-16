package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.*;
import com.pearlyadana.rakhapuraapp.model.response.*;
import com.pearlyadana.rakhapuraapp.service.AttendanceService;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import com.pearlyadana.rakhapuraapp.util.AttendanceExcelGenerator;
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
@RequestMapping(value = "/api/v1/attendances", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AttendanceController {

    @Autowired
    private ExamService examService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.attendanceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/student-class/present/{id}")
    public ResponseEntity<List<AttendanceDto>> findByPresentStudentClassId(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.attendanceService.findByPresentStudentClassId(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<AttendanceDto>> findAll() {
        return new ResponseEntity<>(this.attendanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment/not-present/search")
    public ResponseEntity<PaginationResponse<AttendanceDto>> findEachNotPresentPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long subjectTypeId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.attendanceService.findEachNotPresentPageBySearchingSortByCreatedTimestamp(page, isAscending, academicYearId, examTitleId, subjectTypeId, keyword), HttpStatus.OK);
    }

    private TableHeader setTableHeader(Long academicYearId, Long examTitleId, Long gradeId, String keyword) {
        TableHeader tableHeader = new TableHeader();
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId);
        List<CustomExam> customExamList = new ArrayList<>();
        for(ExamDto examDto : examDtoList) {
            CustomExam customExam = new CustomExam();
            customExam.setExam(examDto);
            customExamList.add(customExam);
        }

        if(!customExamList.isEmpty()) {
            tableHeader.setAcademicYear(customExamList.get(0).getExam().getAcademicYear().getName());
            tableHeader.setExamTitle(customExamList.get(0).getExam().getExamTitle().getName());
            tableHeader.setGrade(customExamList.get(0).getExam().getSubjectType().getGrade().getName());
        }
        tableHeader.setCustomExamList(customExamList);
        return tableHeader;
    }

    @GetMapping("/segment/present/search")
    public ResponseEntity<CustomPaginationResponse<ResultResponse>> findEachPresentPageBySearching(@RequestParam int page, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam String keyword) {
        CustomPaginationResponse<ResultResponse> customPaginationResponse = this.attendanceService.findEachPageBySearching(page, academicYearId, examTitleId, gradeId, keyword);

        for(ResultResponse element : customPaginationResponse.getElements()) {
            List<AttendanceDto> attendedExamList = this.attendanceService.findByStudentClassId(element.getAttendance().getStudentClass().getId());
            element.setAttendedExamList(attendedExamList);
        }

        TableHeader tableHeader = this.setTableHeader(academicYearId, examTitleId, gradeId, keyword);
        customPaginationResponse.setTableHeader(tableHeader);
        return new ResponseEntity<>(customPaginationResponse, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ATTENDANCE_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        for(UUID id : idList) {
            AttendanceDto dto = this.attendanceService.findById(id);
            dto.setPresent(true);
            if(this.attendanceService.update(dto, id) == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            createdList.add(id);
        }
        if(!createdList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CREATED.value(), createdList.size(), 0);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long gradeId, @RequestParam String keyword, HttpServletResponse response) throws IOException {
        CustomPaginationResponse<ResultResponse> customPaginationResponse = new CustomPaginationResponse<>();
        List<ResultResponse> resultResponseList = this.attendanceService.findBySearching(academicYearId, examTitleId, gradeId, keyword);
        for(ResultResponse element : resultResponseList) {
            List<AttendanceDto> attendedExamList = this.attendanceService.findByStudentClassId(element.getAttendance().getStudentClass().getId());
            element.setAttendedExamList(attendedExamList);
        }

        TableHeader tableHeader = this.setTableHeader(academicYearId, examTitleId, gradeId, keyword);
        customPaginationResponse.setElements(resultResponseList);
        customPaginationResponse.setTableHeader(tableHeader);
        response.setContentType("application/octet-stream");
        AttendanceExcelGenerator generator = new AttendanceExcelGenerator(customPaginationResponse);
        generator.export(response);
    }

}
