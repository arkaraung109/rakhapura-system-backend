package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

public interface StudentHostelService {

    PaginationResponse<StudentClassDto> findEachNotPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<StudentClassDto> findEachNotPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String keyword);

    PaginationResponse<StudentClassDto> findEachPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<StudentClassDto> findEachPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, Long hostelId, String keyword);

}
