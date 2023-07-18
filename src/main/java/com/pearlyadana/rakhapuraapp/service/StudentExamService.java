package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;

import java.util.List;
import java.util.UUID;

public interface StudentExamService {

    StudentExamDto findById(UUID id);

    List<StudentExamDto> findAll();

}
