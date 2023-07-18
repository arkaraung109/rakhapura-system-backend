package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {

    AttendanceDto findById(UUID id);

    List<AttendanceDto> findByStudentClassId(UUID id);

    List<AttendanceDto> findAll();

    List<AttendanceDto> findByOrderByCreatedTimestampAsc();

    PaginationResponse<AttendanceDto> findEachNotPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<AttendanceDto> findEachNotPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword);

    PaginationResponse<AttendanceDto> findEachPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending);

    PaginationResponse<AttendanceDto> findEachPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long gradeId, String studentClass, String keyword);

    AttendanceDto save(AttendanceDto attendanceDto);

    AttendanceDto update(AttendanceDto attendanceDto, UUID id);

}
