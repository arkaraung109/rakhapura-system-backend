package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface StudentHostelService {

    List<StudentClassDto> findAllBySearching(Long examTitleId, Long academicYearId, Long gradeId, Long hostelId, String keyword);

    PaginationResponse<StudentClassDto> findEachNotPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String keyword);

    PaginationResponse<StudentClassDto> findEachPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, Long hostelId, String keyword);

}
