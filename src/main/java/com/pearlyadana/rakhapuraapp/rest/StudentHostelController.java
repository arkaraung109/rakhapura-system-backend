package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.HostelDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.StudentClassService;
import com.pearlyadana.rakhapuraapp.service.StudentHostelService;
import com.pearlyadana.rakhapuraapp.util.StudentHostelExcelGenerator;
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
@RequestMapping(value = "/api/v1/student-hostels", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentHostelController {

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentHostelService studentHostelService;

    @GetMapping("/segment/not-present/search")
    public ResponseEntity<PaginationResponse<StudentClassDto>> findEachNotPresentPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.studentHostelService.findEachNotPresentPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId, keyword), HttpStatus.OK);
    }

    @GetMapping("/segment/present/search")
    public ResponseEntity<PaginationResponse<StudentClassDto>> findEachPresentPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam Long hostelId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.studentHostelService.findEachPresentPageBySearchingSortByCreatedTimestamp(page, isAscending, examTitleId, academicYearId, gradeId, hostelId, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.HOSTEL_ATTENDANCE_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestBody StudentClassDto body, @RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        for(UUID id : idList) {
            StudentClassDto dto = this.studentClassService.findById(id);
            HostelDto hostelDto = new HostelDto();
            hostelDto.setId(body.getHostel().getId());
            dto.setHostel(hostelDto);
            if(this.studentClassService.update(dto, id) == null) {
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.HOSTEL_ATTENDANCE_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody StudentClassDto body, @PathVariable("id") UUID id) {
        StudentClassDto dto = this.studentClassService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.getRegNo() != null && dto.getRegSeqNo() != 0) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"used object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        HostelDto hostelDto = new HostelDto();
        hostelDto.setId(body.getHostel().getId());
        dto.setHostel(hostelDto);
        if(this.studentClassService.update(dto, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.HOSTEL_ATTENDANCE_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") UUID id) {
        StudentClassDto dto = this.studentClassService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.getRegNo() != null && dto.getRegSeqNo() != 0) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"used object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.studentClassService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam Long hostelId, @RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<StudentClassDto> studentClassDtoList = this.studentHostelService.findAllBySearching(examTitleId, academicYearId, gradeId, hostelId, keyword);
        StudentHostelExcelGenerator generator = new StudentHostelExcelGenerator(studentClassDtoList);
        generator.export(response);
    }

}
