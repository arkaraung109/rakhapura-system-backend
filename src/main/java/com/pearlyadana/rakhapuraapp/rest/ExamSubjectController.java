package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.request.ExamSubjectDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import com.pearlyadana.rakhapuraapp.service.ExamSubjectService;
import com.pearlyadana.rakhapuraapp.service.StudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/exam-subjects", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ExamSubjectController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamSubjectService examSubjectService;

    @Autowired
    private StudentExamService studentExamService;

    @GetMapping("/{id}")
    public ResponseEntity<ExamSubjectDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.examSubjectService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ExamSubjectDto>> findAll() {
        return new ResponseEntity<>(this.examSubjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/exam/{id}")
    public ResponseEntity<List<ExamSubjectDto>> findAllByAuthorizedExam(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.examSubjectService.findAllByAuthorizedExam(id), HttpStatus.OK);
    }

    @GetMapping("/authorized")
    public ResponseEntity<List<ExamSubjectDto>> findAllByAuthorizedStatus() {
        return new ResponseEntity<>(this.examSubjectService.findAllByAuthorizedStatus(true), HttpStatus.OK);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<ExamSubjectDto> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long subjectTypeId, @RequestParam Long subjectId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.examSubjectService.findEachPageBySearchingSortById(page, isAscending, academicYearId, examTitleId, subjectTypeId, subjectId, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody ExamSubjectDto body) {
        ExamDto examDto = this.examService.findByAcademicYearAndExamTitleAndSubjectType(body.getExam().getAcademicYear().getId(), body.getExam().getExamTitle().getId(), body.getExam().getSubjectType().getId());
        body.setExam(examDto);

        if(!this.examSubjectService.findAllByExamAndSubject(body.getExam().getId(), body.getSubject().getId()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(!this.studentExamService.findAllByExam(body.getExam().getId()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"used object cannot be created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        List<ExamSubjectDto> examSubjectDtoList = this.examSubjectService.findAllByExam(examDto.getId());
        int sumOfPassMark = body.getPassMark();
        int sumOfMarkPercentage = body.getMarkPercentage();
        int overAllPassMark = examDto.getPassMark();
        int overAllMarkPercentage = examDto.getMarkPercentage();
        for(ExamSubjectDto examSubjectDto : examSubjectDtoList) {
            sumOfPassMark += examSubjectDto.getPassMark();
            sumOfMarkPercentage += examSubjectDto.getMarkPercentage();
        }

        String message = "";
        if(sumOfPassMark > overAllPassMark) {
            message += "passMarkExceeded";
            if(sumOfMarkPercentage > overAllMarkPercentage) {
                message += "&markPercentageExceeded";
            }
        } else if(sumOfMarkPercentage > overAllMarkPercentage) {
            message += "markPercentageExceeded";
        }

        if(!message.equalsIgnoreCase("")) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(), message);
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }

        if(this.examSubjectService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody ExamSubjectDto body, @PathVariable("id") Long id) {
        ExamSubjectDto dto = this.examSubjectService.findById(id);
        ExamDto examDto = this.examService.findByAcademicYearAndExamTitleAndSubjectType(body.getExam().getAcademicYear().getId(), body.getExam().getExamTitle().getId(), body.getExam().getSubjectType().getId());
        body.setExam(examDto);

        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(!this.examSubjectService.findAllByExamAndSubject(body.getExam().getId(), body.getSubject().getId()).isEmpty() && (!body.getExam().getId().equals(dto.getExam().getId()) || !body.getSubject().getId().equals(dto.getSubject().getId()))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        List<ExamSubjectDto> examSubjectDtoList = this.examSubjectService.findAllByExam(examDto.getId());
        int sumOfPassMark = body.getPassMark();
        int sumOfMarkPercentage = body.getMarkPercentage();
        int overAllPassMark = examDto.getPassMark();
        int overAllMarkPercentage = examDto.getMarkPercentage();
        for(ExamSubjectDto examSubjectDto : examSubjectDtoList) {
            if(!examSubjectDto.getId().equals(id)) {
                sumOfPassMark += examSubjectDto.getPassMark();
                sumOfMarkPercentage += examSubjectDto.getMarkPercentage();
            }
        }

        String message = "";
        if(sumOfPassMark > overAllPassMark) {
            message += "passMarkExceeded";
            if(sumOfMarkPercentage > overAllMarkPercentage) {
                message += "&markPercentageExceeded";
            }
        } else if(sumOfMarkPercentage > overAllMarkPercentage) {
            message += "markPercentageExceeded";
        }

        if(!message.equalsIgnoreCase("")) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(), message);
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(this.examSubjectService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        ExamSubjectDto dto = this.examSubjectService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.examSubjectService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.examSubjectService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        this.examSubjectService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
