package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    StudentDto findById(UUID id);

    List<StudentDto> findAll();

    List<StudentDto> findAllByNrc(String nrc);

    PaginationResponse<StudentDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<StudentDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long regionId, String keyword);

    StudentDto save(StudentDto studentDto);

    StudentDto update(StudentDto studentDto, UUID id);

    void deleteById(UUID id);

}
