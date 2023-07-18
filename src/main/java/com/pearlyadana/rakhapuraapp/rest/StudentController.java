package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import com.pearlyadana.rakhapuraapp.service.StudentService;
import com.pearlyadana.rakhapuraapp.util.StudentExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentClassService studentClassService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentDto>> findAll() {
        return new ResponseEntity<>(this.studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<StudentDto> findEachPageSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentService.findEachPageSortByCreatedTimestamp(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<StudentDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long regionId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.studentService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, regionId, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody StudentDto body) {
        if(!this.studentService.findAllByNrc(body.getNrc()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.studentService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody StudentDto body, @PathVariable("id") UUID id) {
        StudentDto dto = this.studentService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(!this.studentService.findAllByNrc(body.getNrc()).isEmpty() && !body.getNrc().equals(dto.getNrc())) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        body.setRegDate(dto.getRegDate());
        body.setCreatedTimestamp(dto.getCreatedTimestamp());
        if(this.studentService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") UUID id) {
        if(this.studentService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(!this.studentClassService.findAllByStudentId(id).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"assigned object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.studentService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<StudentDto> studentDtoList = this.studentService.findByOrderByCreatedTimestampAsc();
        StudentExcelGenerator generator = new StudentExcelGenerator(studentDtoList);
        generator.export(response);
    }

}
