package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.AcademicYearDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;


public interface AcademicYearService {

    AcademicYearDto findById(Long id);

    List<AcademicYearDto> findAll();

    List<AcademicYearDto> findAllByName(String name);

    List<AcademicYearDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<AcademicYearDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<AcademicYearDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    AcademicYearDto save(AcademicYearDto academicYearDto);

    AcademicYearDto update(AcademicYearDto academicYearDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
