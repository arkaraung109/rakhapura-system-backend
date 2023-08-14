package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.StudentExam;
import com.pearlyadana.rakhapuraapp.mapper.StudentExamMapper;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;
import com.pearlyadana.rakhapuraapp.repository.StudentExamRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentExamServiceImpl implements StudentExamService {

    @Autowired
    private StudentExamRepository studentExamRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentExamMapper mapper = Mappers.getMapper(StudentExamMapper.class);

    @Transactional(readOnly = true)
    @Override
    public Integer findTotalMark(UUID attendanceId) {
        return this.studentExamRepository.findTotalMark(attendanceId);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer findResult(UUID attendanceId) {
        Integer result = this.studentExamRepository.findResult(attendanceId);
        if(result == null) {
            result = 0;
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public StudentExamDto findById(UUID id) {
        Optional<StudentExam> optional = this.studentExamRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentExamDto findByExamSubjectAndAttendance(Long examSubjectId, UUID attendanceId) {
        Optional<StudentExam> optional = this.studentExamRepository.findFirstByExamSubjectIdAndAttendanceId(examSubjectId, attendanceId);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentExamDto> findAllByExam(Long id) {
        return this.studentExamRepository.findAllByExamId(id)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentExamDto> findAllByAttendance(UUID id) {
        return this.studentExamRepository.findAllByAttendanceIdOrderBySubjectIdAsc(id)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentExamDto> findAll() {
        return this.studentExamRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public StudentExamDto save(StudentExamDto studentExamDto) {
        return this.mapper.mapEntityToDto(this.studentExamRepository.save(this.mapper.mapDtoToEntity(studentExamDto)));
    }

    @Transactional
    @Override
    public StudentExamDto update(StudentExamDto studentExamDto) {
        return this.mapper.mapEntityToDto(this.studentExamRepository.save(this.mapper.mapDtoToEntity(studentExamDto)));
    }

}
