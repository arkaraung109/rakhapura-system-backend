package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AwardDto;
import com.pearlyadana.rakhapuraapp.model.request.PunishmentDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.DataResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.PunishmentService;
import com.pearlyadana.rakhapuraapp.util.AwardExcelGenerator;
import com.pearlyadana.rakhapuraapp.util.PunishmentExcelGenerator;
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
@RequestMapping(value = "/api/v1/punishments", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PunishmentController {

    @Autowired
    private PunishmentService punishmentService;

    @GetMapping("/{id}")
    public ResponseEntity<PunishmentDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.punishmentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<PunishmentDto>> findAll() {
        return new ResponseEntity<>(this.punishmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<PunishmentDto>> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.punishmentService.findEachPageBySearchingSortById(page, isAscending, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<DataResponse> save(@RequestBody PunishmentDto body, @RequestParam List<UUID> idList) {
        List<UUID> createdList = new ArrayList<>();
        List<UUID> errorList = new ArrayList<>();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            body.setEventDate(outputDateFormat.format(inputDateFormat.parse(body.getEventDate())));
            body.setStartDate(outputDateFormat.format(inputDateFormat.parse(body.getStartDate())));
            body.setEndDate(outputDateFormat.format(inputDateFormat.parse(body.getEndDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        for(UUID id : idList) {
            if(!this.punishmentService.findAllByPunishmentAndEventDateAndStudent(body.getPunishment(), body.getEventDate(), id).isEmpty()) {
                errorList.add(id);
            } else {
                StudentDto studentDto = new StudentDto();
                studentDto.setId(id);
                body.setStudent(studentDto);
                if(this.punishmentService.save(body) == null) {
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
    public ResponseEntity<CustomHttpResponse> update(@RequestBody PunishmentDto body, @PathVariable("id") Long id) {
        PunishmentDto dto = this.punishmentService.findById(id);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            body.setEventDate(outputDateFormat.format(inputDateFormat.parse(body.getEventDate())));
            body.setStartDate(outputDateFormat.format(inputDateFormat.parse(body.getStartDate())));
            body.setEndDate(outputDateFormat.format(inputDateFormat.parse(body.getEndDate())));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(!this.punishmentService.findAllByPunishmentAndEventDateAndStudent(body.getPunishment(), body.getEventDate(), body.getStudent().getId()).isEmpty() && ((!body.getPunishment().equals(dto.getPunishment()) || !body.getEventDate().equals(dto.getEventDate())))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.punishmentService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        PunishmentDto dto = this.punishmentService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        this.punishmentService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<PunishmentDto> punishmentDtoList = this.punishmentService.findAllBySearching(keyword);
        PunishmentExcelGenerator generator = new PunishmentExcelGenerator(punishmentDtoList);
        generator.export(response);
    }

}
