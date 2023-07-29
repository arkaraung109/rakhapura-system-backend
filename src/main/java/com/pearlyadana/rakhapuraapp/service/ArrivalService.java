package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface ArrivalService {

    List<StudentClassDto> findAllBySearching(Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword);

    PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean arrival, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword);

}
