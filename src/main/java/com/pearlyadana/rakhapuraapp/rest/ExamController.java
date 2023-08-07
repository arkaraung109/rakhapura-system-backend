package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/exams", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.examService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ExamDto>> findAllFilteredByAcademicYearAndExamTitle(@RequestParam Long academicYearId, @RequestParam Long examTitleId) {
        return new ResponseEntity<>(this.examService.findAllFilteredByAcademicYearAndExamTitle(academicYearId, examTitleId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ExamDto>> findAll() {
        return new ResponseEntity<>(this.examService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<ExamDto>> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long subjectTypeId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.examService.findEachPageBySearchingSortById(page, isAscending, academicYearId, examTitleId, subjectTypeId, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody ExamDto body) {
        if(!this.examService.findAllByAcademicYearAndExamTitleAndSubjectType(body.getAcademicYear().getId(), body.getExamTitle().getId(), body.getSubjectType().getId()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.examService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody ExamDto body, @PathVariable("id") Long id) {
        ExamDto dto = this.examService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(!this.examService.findAllByAcademicYearAndExamTitleAndSubjectType(body.getAcademicYear().getId(), body.getExamTitle().getId(), body.getSubjectType().getId()).isEmpty() && (!body.getAcademicYear().getId().equals(dto.getAcademicYear().getId()) || !body.getExamTitle().getId().equals(dto.getExamTitle().getId()) || !body.getSubjectType().getId().equals(dto.getSubjectType().getId()))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.examService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ AuthoritiesConstants.EXAM_ENTRY, AuthoritiesConstants.ADMIN })
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        ExamDto dto = this.examService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.examService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.examService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        this.examService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
