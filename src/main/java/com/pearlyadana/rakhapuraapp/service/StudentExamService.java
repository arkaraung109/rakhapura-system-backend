package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;

import java.util.List;
import java.util.UUID;

public interface StudentExamService {

    Integer findTotalMark(UUID attendanceId);

    Integer findResult(UUID attendanceId);

    StudentExamDto findById(UUID id);

    StudentExamDto findByExamSubjectAndAttendance(Long examSubjectId, UUID attendanceId);

    List<StudentExamDto> findAllByExam(Long id);

    List<StudentExamDto> findAllByAttendance(UUID id);

    List<StudentExamDto> findAll();

    StudentExamDto save(StudentExamDto studentExamDto);

    StudentExamDto update(StudentExamDto studentExamDto);

}
