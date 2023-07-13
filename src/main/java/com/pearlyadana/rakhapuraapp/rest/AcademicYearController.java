package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.AcademicYearDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/academic-years", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AcademicYearController {

    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.academicYearService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<AcademicYearDto>> findAll() {
        return new ResponseEntity<>(this.academicYearService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/authorized")
    public ResponseEntity<List<AcademicYearDto>> findAllByAuthorizedStatus() {
        return new ResponseEntity<>(this.academicYearService.findAllByAuthorizedStatus(true), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<AcademicYearDto> findEachPageSortById(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.academicYearService.findEachPageSortById(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<AcademicYearDto> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.academicYearService.findEachPageBySearchingSortById(page, isAscending, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody AcademicYearDto body) {
        if(!this.academicYearService.findAllByName(body.getName()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.academicYearService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody AcademicYearDto body, @PathVariable("id") Long id) {
        AcademicYearDto dto = this.academicYearService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(!this.academicYearService.findAllByName(body.getName()).isEmpty() && !body.getName().equals(dto.getName())) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(this.academicYearService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ AuthoritiesConstants.EXAM_ENTRY, AuthoritiesConstants.ADMIN })
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        AcademicYearDto dto = this.academicYearService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.academicYearService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.academicYearService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        this.academicYearService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
