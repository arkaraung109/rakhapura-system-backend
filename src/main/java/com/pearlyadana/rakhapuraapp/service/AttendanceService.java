package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.CustomPaginationResponse;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.model.response.ResultResponse;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {

    AttendanceDto findById(UUID id);

    List<AttendanceDto> findByStudentClassId(UUID id);

    List<AttendanceDto> findByPresentStudentClassId(UUID id);

    List<AttendanceDto> findAll();

    PaginationResponse<AttendanceDto> findEachNotPresentPageBySearching(int pageNumber, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword);

    CustomPaginationResponse<ResultResponse> findEachPageBySearching(int pageNumber, Long academicYearId, Long examTitleId, Long gradeId, String keyword);

    List<ResultResponse> findBySearching(Long academicYearId, Long examTitleId, Long gradeId, String keyword);

    CustomPaginationResponse<ResultResponse> findEachModerationPageBySearching(int pageNumber, Long academicYearId, Long examTitleId, Long gradeId, String keyword);

    List<ResultResponse> findModerationBySearching(Long academicYearId, Long examTitleId, Long gradeId, String keyword);

    AttendanceDto save(AttendanceDto attendanceDto);

    AttendanceDto update(AttendanceDto attendanceDto, UUID id);

}
