package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.ExamTitleDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface ExamTitleService {

    ExamTitleDto findById(Long id);

    List<ExamTitleDto> findAll();

    List<ExamTitleDto> findAllByName(String name);

    List<ExamTitleDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<ExamTitleDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    ExamTitleDto save(ExamTitleDto examTitleDto);

    ExamTitleDto update(ExamTitleDto examTitleDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
