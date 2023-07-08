package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

public interface ArrivalService {

    PaginationResponse<StudentClassDto> findEachArrivalPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<StudentClassDto> findEachArrivalPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword);

}
