package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.AttendanceService;
import com.pearlyadana.rakhapuraapp.util.AttendenceExcelGenerator;
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
    private AttendanceService attendanceService;

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.attendanceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<AttendanceDto>> findAll() {
        return new ResponseEntity<>(this.attendanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<AttendanceDto> findEachPageSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, boolean present) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.attendanceService.findEachPageSortByCreatedTimestamp(page, isAscending, present);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<AttendanceDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, boolean present, @RequestParam Long academicYearId, @RequestParam Long examTitleId, @RequestParam Long subjectTypeId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.attendanceService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, present, academicYearId, examTitleId, subjectTypeId, keyword);
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
    public void exportToExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<AttendanceDto> attendenceDtoList = this.attendanceService.findAll();
        AttendenceExcelGenerator generator = new AttendenceExcelGenerator(attendenceDtoList);
        generator.export(response);
    }

}
