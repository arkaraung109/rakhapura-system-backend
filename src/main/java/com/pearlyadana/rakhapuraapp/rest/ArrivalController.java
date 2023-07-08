package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.ArrivalService;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import com.pearlyadana.rakhapuraapp.util.ArrivedStudentExcelGenerator;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/arrivals", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ArrivalController {

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private ArrivalService arrivalService;

    @GetMapping("/segment")
    public PaginationResponse<StudentClassDto> findEachArrivalPageSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.arrivalService.findEachArrivalPageSortByCreatedTimestamp(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<StudentClassDto> findEachArrivalPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String studentClass, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.arrivalService.findEachArrivalPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId, studentClass, keyword);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@PathVariable("id") UUID id) {
        StudentClassDto dto = this.studentClassService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        dto.setArrival(true);
        if(this.studentClassService.update(dto, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<StudentClassDto> studentClassDtoList = (List<StudentClassDto>) this.studentClassService.findAll().stream().filter(StudentClassDto::isArrival).collect(Collectors.toList());
        ArrivedStudentExcelGenerator generator = new ArrivedStudentExcelGenerator(studentClassDtoList);
        generator.export(response);
    }

}
