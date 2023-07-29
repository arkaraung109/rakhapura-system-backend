package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.ClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface ClassService {

    ClassDto findById(Long id);

    List<String> findDistinctAll();

    List<ClassDto> findAll();

    List<ClassDto> findAllByNameAndAcademicYearAndGrade(String name, Long academicYearId, Long gradeId);

    List<ClassDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    List<ClassDto> findAllFilteredByAcademicYearAndGrade(Long academicYearId, Long gradeId);

    PaginationResponse<ClassDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long gradeId, String keyword);

    ClassDto save(ClassDto classDto);

    ClassDto update(ClassDto classDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
