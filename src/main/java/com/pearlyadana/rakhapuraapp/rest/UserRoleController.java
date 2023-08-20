package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.model.response.UserRoleDto;
import com.pearlyadana.rakhapuraapp.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("")
    public ResponseEntity<List<UserRoleDto>> findAll() {
        return new ResponseEntity<>(this.userRoleService.findAll(), HttpStatus.OK);
    }

}
