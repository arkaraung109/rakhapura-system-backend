package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

public interface ArrivalService {

    PaginationResponse<StudentClassDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean arrival);

    PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean arrival, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword);

}
