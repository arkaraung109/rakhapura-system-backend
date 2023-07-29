package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/arrivals", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ArrivalController {

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private ArrivalService arrivalService;

    @GetMapping("/segment/search")
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam boolean arrival, @RequestParam Long examTitleId, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String studentClass, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.arrivalService.findEachPageBySearchingSortByCreatedTimestamp(page, isAscending, arrival, examTitleId, academicYearId, gradeId, studentClass, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.STUDENT_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        for(UUID id : idList) {
            StudentClassDto dto = this.studentClassService.findById(id);
            dto.setArrival(true);
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

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam Long examTitleId,@RequestParam Long academicYearId,@RequestParam Long gradeId, @RequestParam String studentClass, @RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<StudentClassDto> studentClassDtoList = this.arrivalService.findAllBySearching(examTitleId, academicYearId, gradeId, studentClass, keyword);
        ArrivedStudentExcelGenerator generator = new ArrivedStudentExcelGenerator(studentClassDtoList);
        generator.export(response);
    }

}
