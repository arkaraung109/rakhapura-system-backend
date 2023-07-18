package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;
import com.pearlyadana.rakhapuraapp.service.StudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/student-exams", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StudentExamController {

    @Autowired
    private StudentExamService studentExamService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentExamDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.studentExamService.findById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentExamDto>> findAll() {
        return new ResponseEntity<>(this.studentExamService.findAll(), HttpStatus.OK);
    }



}
