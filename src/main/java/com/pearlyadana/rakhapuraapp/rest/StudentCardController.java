package com.pearlyadana.rakhapuraapp.rest;

import com.itextpdf.text.DocumentException;
import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentCard;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<StudentClassDto>> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.studentCardService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public void generate(@RequestBody StudentCard body, HttpServletResponse response) throws DocumentException, IOException {
        List<StudentClassDto> studentClassDtoList = new ArrayList<>();
        List<UUID> idList = body.getIdList();
        List<ExamDto> examDtoList = this.examService.findAllFilteredByAcademicYearAndExamTitleAndGrade(body.getAcademicYearId(), body.getExamTitleId(), body.getGradeId());
        for(UUID id : idList) {
            StudentClassDto studentClassDto = this.studentClassService.findById(id);
            studentClassDtoList.add(studentClassDto);
        }
        this.studentCardService.generate(body, studentClassDtoList, examDtoList, response);
    }

}
