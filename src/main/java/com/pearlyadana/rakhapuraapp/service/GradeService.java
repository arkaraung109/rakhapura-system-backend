package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.GradeDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface GradeService {

    GradeDto findById(Long id);

    List<GradeDto> findAll();

    List<GradeDto> findAllByName(String name);

    List<GradeDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<GradeDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<GradeDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    GradeDto save(GradeDto gradeDto);

    GradeDto update(GradeDto gradeDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
