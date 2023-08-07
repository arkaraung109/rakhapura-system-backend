package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.http.SecurityUtils;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.service.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/app-users", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationUserController {

    @Autowired
    private UserTableService userTableService;

    @GetMapping("")
    public ResponseEntity<ApplicationUserDto> getApplicationUserByUsername(final Authentication authentication) {
        String userId= SecurityUtils.getUserId(authentication);
        Optional<ApplicationUserDto> dto = this.userTableService.findUserTableByLoginUsername(userId);
        if(dto.isPresent()) {
            return new ResponseEntity<>(dto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
