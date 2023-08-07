package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.ClassDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/classes", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.classService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/distinct")
    public ResponseEntity<List<String>> findDistinctAll() {
        return new ResponseEntity<>(this.classService.findDistinctAll(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ClassDto>> findAll() {
        return new ResponseEntity<>(this.classService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/authorized")
    public ResponseEntity<List<ClassDto>> findAllByAuthorizedStatus() {
        return new ResponseEntity<>(this.classService.findAllByAuthorizedStatus(true), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ClassDto>> findAllFilteredByAcademicYearAndGrade(@RequestParam Long academicYearId, @RequestParam Long gradeId) {
        return new ResponseEntity<>(this.classService.findAllFilteredByAcademicYearAndGrade(academicYearId, gradeId), HttpStatus.OK);
    }

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<ClassDto>> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long academicYearId, @RequestParam Long gradeId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.classService.findEachPageBySearchingSortById(page, isAscending, academicYearId, gradeId, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody ClassDto body) {
        if(!this.classService.findAllByNameAndAcademicYearAndGrade(body.getName(), body.getAcademicYear().getId(), body.getGrade().getId()).isEmpty()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.classService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@RequestBody ClassDto body, @PathVariable("id") Long id) {
        ClassDto dto = this.classService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(!this.classService.findAllByNameAndAcademicYearAndGrade(body.getName(), body.getAcademicYear().getId(), body.getGrade().getId()).isEmpty() && (!body.getName().equals(dto.getName()) || !body.getAcademicYear().getId().equals(dto.getAcademicYear().getId()) || !body.getGrade().getId().equals(dto.getGrade().getId()))) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(this.classService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ AuthoritiesConstants.EXAM_ENTRY, AuthoritiesConstants.ADMIN })
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        ClassDto dto = this.classService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.classService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.classService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        this.classService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
