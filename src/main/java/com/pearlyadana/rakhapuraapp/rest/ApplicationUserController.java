package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.AuthoritiesConstants;
import com.pearlyadana.rakhapuraapp.http.SecurityUtils;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.service.UserTableService;
import com.pearlyadana.rakhapuraapp.util.ApplicationUserExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/app-users", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationUserController {

    @Autowired
    private UserTableService userTableService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.userTableService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApplicationUserDto> getApplicationUserByUsername(final Authentication authentication) {
        String userId= SecurityUtils.getUserId(authentication);
        Optional<ApplicationUserDto> dto = this.userTableService.findUserTableByLoginUsername(userId);
        return dto.map(applicationUserDto -> new ResponseEntity<>(applicationUserDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/segment/search")
    public ResponseEntity<PaginationResponse<ApplicationUserDto>> findEachPageBySearchingSortById(@RequestParam int page, @RequestParam(required = false) String order, @RequestParam Long roleId, @RequestParam String keyword) {
        boolean isAscending = true;
        if(order!=null && order.equals("desc")) {
            isAscending = false;
        }
        return new ResponseEntity<>(this.userTableService.findEachPageBySearchingSortById(page, isAscending, roleId, keyword), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> save(@RequestBody ApplicationUserDto body) {
        if(this.userTableService.findUserTableByLoginUsername(body.getLoginUserName()).isPresent()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        if(this.userTableService.save(body) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CREATED.value(),"new object is created.");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/profile/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.ATTENDANCE_ENTRY, AuthoritiesConstants.EXAM_ENTRY, AuthoritiesConstants.EXAM_MARK_ENTRY, AuthoritiesConstants.HOSTEL_ATTENDANCE_ENTRY, AuthoritiesConstants.STUDENT_ENTRY })
    public ResponseEntity<CustomHttpResponse> update(@RequestBody ApplicationUserDto body, @PathVariable("id") Long id) {
        ApplicationUserDto dto = this.userTableService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(!dto.isActiveStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"disabled object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(!passwordEncoder.matches(body.getOldPassword(), dto.getPassword())) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"old password is wrong.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(this.userTableService.findUserTableByLoginUsername(body.getLoginUserName()).isPresent() && !body.getLoginUserName().equals(dto.getLoginUserName())) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(body.getPassword().equals("")) {
            body.setPassword(dto.getPassword());
        } else {
            body.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        if(this.userTableService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> updateApplicationUser(@RequestBody ApplicationUserDto body, @PathVariable("id") Long id) {
        ApplicationUserDto dto = this.userTableService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if(!dto.isActiveStatus()) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_ACCEPTABLE.value(),"disabled object cannot be updated.");
            return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        if(this.userTableService.findUserTableByLoginUsername(body.getLoginUserName()).isPresent() && !body.getLoginUserName().equals(dto.getLoginUserName())) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.CONFLICT.value(),"object has already been created.");
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        if(body.getPassword().equals("")) {
            body.setPassword(dto.getPassword());
        } else {
            body.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        if(this.userTableService.update(body, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"object is updated.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CustomHttpResponse> changeActiveStatus(@PathVariable("id") Long id, @RequestParam boolean activeStatus) {
        ApplicationUserDto dto = this.userTableService.findById(id);
        if(dto == null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.NOT_FOUND.value(),"object does not exist.");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        dto.setActiveStatus(activeStatus);
        if(this.userTableService.update(dto, id) != null) {
            CustomHttpResponse res = new CustomHttpResponse(HttpStatus.OK.value(),"status is changed.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcelFile(@RequestParam Long roleId, @RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        List<ApplicationUserDto> applicationUserDtoList = this.userTableService.findAllBySearching(roleId, keyword);
        ApplicationUserExcelGenerator generator = new ApplicationUserExcelGenerator(applicationUserDtoList);
        generator.export(response);
    }

}
