package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface ExamService {

    ExamDto findById(Long id);

    ExamDto findByAcademicYearAndExamTitleAndSubjectType(Long academicYearId, Long examTitleId, Long subjectTypeId);

    List<ExamDto> findAllFilteredByAcademicYearAndExamTitle(Long academicYearId, Long examTitleId);

    List<ExamDto> findAllFilteredByAcademicYearAndExamTitleAndGrade(Long academicYearId, Long examTitleId, Long gradeId);

    List<ExamDto> findAll();

    List<ExamDto> findAllByAcademicYearAndExamTitleAndSubjectType(Long academicYearId, Long examTitleId, Long subjectTypeId);

    PaginationResponse<ExamDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<ExamDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword);

    ExamDto save(ExamDto examDto);

    ExamDto update(ExamDto examDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
