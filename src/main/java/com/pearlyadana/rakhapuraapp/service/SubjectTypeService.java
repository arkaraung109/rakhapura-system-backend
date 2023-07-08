package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.SubjectTypeDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface SubjectTypeService {

    SubjectTypeDto findById(Long id);

    List<String> findDistinctAll();

    List<SubjectTypeDto> findAll();

    List<SubjectTypeDto> findAllByNameAndGrade(String name, Long gradeId);

    List<SubjectTypeDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    List<SubjectTypeDto> findAllFilteredByGrade(Long gradeId);

    PaginationResponse<SubjectTypeDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<SubjectTypeDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long gradeId, String keyword);

    SubjectTypeDto save(SubjectTypeDto subjectTypeDto);

    SubjectTypeDto update(SubjectTypeDto subjectTypeDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
