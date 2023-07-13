package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {

    AttendanceDto findById(UUID id);

    List<AttendanceDto> findAll();

    PaginationResponse<AttendanceDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean present);

    PaginationResponse<AttendanceDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean present, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword);

    AttendanceDto save(AttendanceDto attendanceDto);

    AttendanceDto update(AttendanceDto attendanceDto, UUID id);

}
