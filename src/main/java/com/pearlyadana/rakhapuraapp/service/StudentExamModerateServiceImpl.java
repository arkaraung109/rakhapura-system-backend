package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.StudentExamModerate;
import com.pearlyadana.rakhapuraapp.mapper.StudentExamModerateMapper;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamModerateDto;
import com.pearlyadana.rakhapuraapp.repository.StudentExamModerateRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentExamModerateServiceImpl implements StudentExamModerateService {

    @Autowired
    private StudentExamModerateRepository studentExamModerateRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentExamModerateMapper mapper = Mappers.getMapper(StudentExamModerateMapper.class);

    @Transactional(readOnly = true)
    @Override
    public StudentExamModerateDto findById(UUID id) {
        Optional<StudentExamModerate> optional = this.studentExamModerateRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentExamModerateDto findByExamSubjectAndAttendance(Long examSubjectId, UUID attendanceId) {
        Optional<StudentExamModerate> optional = this.studentExamModerateRepository.findFirstByExamSubjectIdAndAttendanceId(examSubjectId, attendanceId);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional
    @Override
    public StudentExamModerateDto save(StudentExamModerateDto studentExamModerateDto) {
        return this.mapper.mapEntityToDto(this.studentExamModerateRepository.save(this.mapper.mapDtoToEntity(studentExamModerateDto)));
    }

    @Transactional
    @Override
    public void delete(StudentExamModerateDto studentExamModerateDto) {
        this.studentExamModerateRepository.delete(this.mapper.mapDtoToEntity(studentExamModerateDto));
    }

}
