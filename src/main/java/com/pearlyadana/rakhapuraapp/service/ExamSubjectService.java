package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.ExamSubjectDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface ExamSubjectService {

    ExamSubjectDto findById(Long id);

    List<ExamSubjectDto> findAll();

    List<ExamSubjectDto> findAllByExam(Long examId);

    List<ExamSubjectDto> findAllAuthorizedByExam(Long examId);

    List<ExamSubjectDto> findAllByExamAndSubject(Long examId, Long subjectId);

    List<ExamSubjectDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<ExamSubjectDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, Long subjectId, String keyword);

    ExamSubjectDto save(ExamSubjectDto examSubjectDto);

    ExamSubjectDto update(ExamSubjectDto examSubjectDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
