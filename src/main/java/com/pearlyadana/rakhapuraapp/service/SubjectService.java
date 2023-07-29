package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.SubjectDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface SubjectService {

    SubjectDto findById(Long id);

    List<SubjectDto> findAll();

    List<SubjectDto> findAllByName(String name);

    List<SubjectDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<SubjectDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    SubjectDto save(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
