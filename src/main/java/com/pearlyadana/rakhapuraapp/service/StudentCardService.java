package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

public interface StudentCardService {

    PaginationResponse<StudentClassDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword);

}
