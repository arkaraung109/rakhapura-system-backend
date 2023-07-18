package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Class;
import com.pearlyadana.rakhapuraapp.entity.StudentExam;
import com.pearlyadana.rakhapuraapp.mapper.ClassMapper;
import com.pearlyadana.rakhapuraapp.mapper.StudentExamMapper;
import com.pearlyadana.rakhapuraapp.model.request.ClassDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;
import com.pearlyadana.rakhapuraapp.repository.ClassRepository;
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
    public StudentExamDto findById(UUID id) {
        Optional<StudentExam> optional = this.studentExamRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentExamDto> findAll() {
        return this.studentExamRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
