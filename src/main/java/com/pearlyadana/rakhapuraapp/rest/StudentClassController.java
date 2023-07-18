package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import com.pearlyadana.rakhapuraapp.util.StudentClassExcelGenerator;
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
@RequestMapping(value = "/api/v1/student-classes", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentClassController {

    @Autowired
    private StudentClassService studentClassService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentClassDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.studentClassService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentClassDto>> findAll() {
        return new ResponseEntity<>(this.studentClassService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<StudentClassDto> findEachPageSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentClassService.findEachPageSortByCreatedTimestamp(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String studentClass, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentClassService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId, studentClass, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestBody StudentClassDto body, @RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        List<UUID> errorList = new ArrayList<>();
        Long examTitleId = body.getExamTitle().getId();
        Long academicYearId = body.getStudentClass().getAcademicYear().getId();
        for(UUID id : idList) {
            if(!this.studentClassService.findAllByExamTitleAndAcademicYearAndStudent(examTitleId, academicYearId, id).isEmpty()) {
                errorList.add(id);
            } else {
                StudentDto studentDto = new StudentDto();
                studentDto.setId(id);
                body.setStudent(studentDto);
                if(this.studentClassService.save(body) == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                createdList.add(id);
            }
        }
        if(!createdList.isEmpty() && errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CREATED.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        if(!errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CONFLICT.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody StudentClassDto body, @PathVariable("id") UUID id) {
        StudentClassDto dto = this.studentClassService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(!this.studentClassService.findAllByExamTitleAndAcademicYearAndStudent(body.getExamTitle().getId(), body.getStudentClass().getAcademicYear().getId(), body.getStudent().getId()).isEmpty() && (!body.getExamTitle().getId().equals(dto.getExamTitle().getId()) || !body.getStudentClass().getAcademicYear().getId().equals(dto.getStudentClass().getAcademicYear().getId()))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(dto.isArrival()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"arrived object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        body.setRegNo(dto.getRegNo());
        body.setRegSeqNo(dto.getRegSeqNo());
        body.setArrival(dto.isArrival());
        body.setCreatedTimestamp(dto.getCreatedTimestamp());
        body.setHostel(dto.getHostel());
        if(this.studentClassService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") UUID id) {
        StudentClassDto dto = this.studentClassService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(dto.isArrival()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"arrived object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.studentClassService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<StudentClassDto> studentClassDtoList = this.studentClassService.findByOrderByCreatedTimestampAsc();
        StudentClassExcelGenerator generator = new StudentClassExcelGenerator(studentClassDtoList);
        generator.export(response);
    }

}
