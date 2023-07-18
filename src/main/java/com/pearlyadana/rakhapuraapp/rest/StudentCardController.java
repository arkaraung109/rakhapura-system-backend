package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.AttendanceService;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import com.pearlyadana.rakhapuraapp.service.StudentCardService;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/student-cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentCardController {

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentCardService studentCardService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/segment")
    public PaginationResponse<StudentClassDto> findEachPageSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentCardService.findEachPageSortByCreatedTimestamp(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String studentClass, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentCardService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId, studentClass, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<DataResponse> generate(@RequestParam List<UUID> idList, @RequestParam int examHoldingTimes) {
        List<UUID> createdList = new ArrayList<>();
        List<UUID> errorList = new ArrayList<>();
        for(UUID id : idList) {
            StudentClassDto studentClassDto = this.studentClassService.findById(id);
            List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getGrade().getId());
            if(examDtoList.isEmpty()) {
                errorList.add(id);
            } else {
                int maxRegSeqNo = this.studentClassService.findMaxRegSeqNo(studentClassDto.getExamTitle().getId(), studentClassDto.getStudentClass().getAcademicYear().getId(), studentClassDto.getStudentClass().getGrade().getId()) + 1;
                String regSeq = studentClassDto.getStudentClass().getGrade().getAbbreviate() + " - " + this.getRegSeqNoInMyanmar(String.valueOf(maxRegSeqNo));
                studentClassDto.setRegSeqNo(maxRegSeqNo);
                studentClassDto.setRegNo(regSeq);
                if(this.studentClassService.update(studentClassDto, id) == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                createdList.add(id);

                for(ExamDto examDto : examDtoList) {
                    AttendanceDto attendenceDto = new AttendanceDto();
                    attendenceDto.setPresent(false);
                    attendenceDto.setExam(examDto);
                    attendenceDto.setStudentClass(studentClassDto);
                    if(this.attendanceService.save(attendenceDto) == null) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
        }
        if(!createdList.isEmpty() && errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CREATED.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        if(!errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.NOT_FOUND.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRegSeqNoInMyanmar(String numberString) {
        StringBuilder sb = new StringBuilder();
        for(Character chr : numberString.toCharArray()) {
            switch (chr) {
                case '0': sb.append('၀'); break;
                case '1': sb.append('၁'); break;
                case '2': sb.append('၂'); break;
                case '3': sb.append('၃'); break;
                case '4': sb.append('၄'); break;
                case '5': sb.append('၅'); break;
                case '6': sb.append('၆'); break;
                case '7': sb.append('၇'); break;
                case '8': sb.append('၈'); break;
                case '9': sb.append('၉'); break;
            }
        }
        return sb.toString();
    }

}
