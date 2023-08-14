package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.PublicExamResultDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface PublicExamResultService {

    Integer findMaxSerialNo();

    List<StudentClassDto> findPassedAllByAcademicYearAndExamTitleAndGrade(Long academicYearId, Long examTitleId, Long gradeId);

    PaginationResponse<StudentClassDto> findEachPageBySearching(int pageNumber, Long academicYearId, Long examTitleId, Long gradeId, String keyword);

    PublicExamResultDto save(PublicExamResultDto publicExamResultDto);

}
