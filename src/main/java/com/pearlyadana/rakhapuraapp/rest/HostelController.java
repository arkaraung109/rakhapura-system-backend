package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.model.request.HostelDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/hostels", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HostelController {

    @Autowired
    private HostelService hostelService;

    @GetMapping("/{id}")
    public ResponseEntity<HostelDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.hostelService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<HostelDto>> findAll() {
        return new ResponseEntity<>(this.hostelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/authorized")
    public ResponseEntity<List<HostelDto>> findAllByAuthorizedStatus() {
        return new ResponseEntity<>(this.hostelService.findAllByAuthorizedStatus(true), HttpStatus.OK);
    }

    @GetMapping("/segment")
    public PaginationResponse<HostelDto> findEachPageSortById(
            @RequestParam @Min(value = 1, message = "invalid parameter value") int page,
            @RequestParam(required = false) String order) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.hostelService.findEachPageSortById(page, isAscending);
    }

    @GetMapping("/segment/search")
    public PaginationResponse<HostelDto> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return this.hostelService.findEachPageBySearchingSortById(page, isAscending, keyword);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> save(@Valid @RequestBody HostelDto body, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            if(!this.hostelService.findAllByNameAndAddress(body.getName(), body.getAddress()).isEmpty()) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            }
            if(this.hostelService.save(body) != null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.EXAM_ENTRY)
    public ResponseEntity<CustomHttpResponse> update(@Validated @RequestBody HostelDto body, @PathVariable("id") Long id, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            HostelDto dto = this.hostelService.findById(id);
            if(dto == null) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
                return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
            }
            if(!this.hostelService.findAllByNameAndAddress(body.getName(), body.getAddress()).isEmpty() && (!body.getName().equals(dto.getName()) || !body.getAddress().equals(dto.getAddress()))) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            }
            if(dto.isAuthorizedStatus()) {
                CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be updated.");
                return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
            }
            if(this.hostelService.update(body, id) != null) {
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
        HostelDto dto = this.hostelService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        if(dto.isAuthorizedStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"authorized object cannot be deleted.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        this.hostelService.deleteById(id);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is deleted.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/{id}/authorize/{authorizedUserId}")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> authorize(@PathVariable("id") Long id, @PathVariable("authorizedUserId") Long authorizedUserId) {
        if(this.hostelService.findById(id) == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NO_CONTENT.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        this.hostelService.authorizeById(id, authorizedUserId);
        CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is authorized.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
