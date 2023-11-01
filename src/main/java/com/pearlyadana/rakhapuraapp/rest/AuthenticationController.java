package com.pearlyadana.rakhapuraapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pearlyadana.rakhapuraapp.http.TokenProvider;
import com.pearlyadana.rakhapuraapp.model.request.Login;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomHttpResponse;
import com.pearlyadana.rakhapuraapp.service.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/authenticate")
public class AuthenticationController {

    @Autowired
    private TokenProvider jWTTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserTableService userTableService;

    static class JWTToken implements Serializable {
        private String token;

        @JsonProperty("access_token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }
    }

    @PostMapping(value = {"/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> authenticate(@RequestBody Login login) throws Exception {
        try {
            Optional<ApplicationUserDto> dto = this.userTableService.findUserTableByLoginUsername(login.getUserID());
            if(!dto.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                if(!dto.get().isActiveStatus()) {
                    CustomHttpResponse err = new CustomHttpResponse();
                    err.setMessage("Account is disabled.");
                    err.setStatus(HttpStatus.LOCKED.value());
                    return new ResponseEntity<>(err, HttpStatus.LOCKED);
                }
            }
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.getUserID(), login.getPassword()));

            String token = jWTTokenProvider.createToken(authenticate);
            JWTToken res = new JWTToken();
            res.setToken(token);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return new ResponseEntity<>(res, httpHeaders, HttpStatus.OK);
        } catch(UsernameNotFoundException ex) {
            CustomHttpResponse err = new CustomHttpResponse();
            err.setMessage("Account is not found.");
            err.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        } catch(DisabledException ex) {
            CustomHttpResponse err = new CustomHttpResponse();
            err.setMessage("Your account is disabled. Please contact administrator.");
            err.setStatus(HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
        } catch(BadCredentialsException ex) {
            CustomHttpResponse err = new CustomHttpResponse();
            err.setMessage("Username or Password is wrong.");
            err.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
        }
    }

}
