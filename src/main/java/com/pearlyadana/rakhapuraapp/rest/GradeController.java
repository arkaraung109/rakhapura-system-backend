package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.GradeDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/grades", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/{id}")
    public ResponseEntity<GradeDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.gradeService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<GradeDto>> findAll() {
        return new ResponseEntity<>(this.gradeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("authorized")
    public ResponseEntity<List<GradeDto>> findAllByAuthorizedStatus() {
        return new ResponseEntity<>(this.gradeService.findAllByAuthorizedStatus(true), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<GradeDto> findEachPageSortById(@RequestParam int page, @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.gradeService.findEachPageSortById(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<GradeDto> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.gradeService.findEachPageBySearchingSortById(page, isAscending, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@Valid @RequestBody GradeDto body, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            if(!this.gradeService.findAllByName(body.getName()).isEmpty()) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            }
            if(this.gradeService.save(body) != null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@Validated @RequestBody GradeDto body, @PathVariable("id") Long id, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            GradeDto dto = this.gradeService.findById(id);
            if(dto == null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
                return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
            }
            if(!this.gradeService.findAllByName(body.getName()).isEmpty() && !body.getName().equals(dto.getName())) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            }
            if(dto.isAuthorizedStatus()) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
                return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
            }
            if(this.gradeService.update(body, id) != null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({ AuthoritiesConstants.EXAM_ENTRY, AuthoritiesConstants.ADMIN })
    public ResponseEntity<CustomHttpResponse> delete(@PathVariable("id") Long id) {
        GradeDto dto = this.gradeService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.gradeService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.gradeService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        this.gradeService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
