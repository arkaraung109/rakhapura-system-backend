package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentExamModerateDto;

import java.util.UUID;

public interface StudentExamModerateService {

    StudentExamModerateDto findById(UUID id);

    StudentExamModerateDto findByExamSubjectAndAttendance(Long examSubjectId, UUID attendanceId);

    StudentExamModerateDto save(StudentExamModerateDto studentExamModerateDto);

    void delete(StudentExamModerateDto studentExamModerateDto);

}
