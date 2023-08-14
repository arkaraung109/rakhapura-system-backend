package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AwardDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.AwardService;
import com.pearlyadana.rakhapuraapp.util.AwardExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/awards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/{id}")
    public ResponseEntity<AwardDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.awardService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<AwardDto>> findAll() {
        return new ResponseEntity<>(this.awardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<AwardDto>> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.awardService.findEachPageBySearchingSortById(page, isAscending, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestBody AwardDto body, @RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        List<UUID> errorList = new ArrayList<>();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            body.setEventDate(outputDateFormat.format(inputDateFormat.parse(body.getEventDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        for(UUID id : idList) {
            if(!this.awardService.findAllByAwardAndEventDateAndStudent(body.getAward(), body.getEventDate(), id).isEmpty()) {
                errorList.add(id);
            } else {
                StudentDto studentDto = new StudentDto();
                studentDto.setId(id);
                body.setStudent(studentDto);
                if(this.awardService.save(body) == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                createdList.add(id);
            }
        }
        if(!createdList.isEmpty() && errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CREATED.value(), createdList.size(), 0);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        if(!errorList.isEmpty()) {
            DataResponse res = new DataResponse(HttpStatus.CONFLICT.value(), createdList.size(), errorList.size());
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody AwardDto body, @PathVariable("id") Long id) {
        AwardDto dto = this.awardService.findById(id);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            body.setEventDate(outputDateFormat.format(inputDateFormat.parse(body.getEventDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(!this.awardService.findAllByAwardAndEventDateAndStudent(body.getAward(), body.getEventDate(), body.getStudent().getId()).isEmpty() && (!body.getAward().equals(dto.getAward()) || !body.getEventDate().equals(dto.getEventDate()))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.awardService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        AwardDto dto = this.awardService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        this.awardService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<AwardDto> awardDtoList = this.awardService.findAllBySearching(keyword);
        AwardExcelGenerator generator = new AwardExcelGenerator(awardDtoList);
        generator.export(response);
    }

}
