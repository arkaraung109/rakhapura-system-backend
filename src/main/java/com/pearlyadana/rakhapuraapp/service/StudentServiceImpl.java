package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Student;
import com.pearlyadana.rakhapuraapp.mapper.StudentMapper;
import com.pearlyadana.rakhapuraapp.model.request.StudentDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.StudentRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    @Transactional(readOnly = true)
    @Override
    public StudentDto findById(UUID id) {
        Optional<Student> optional = this.studentRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDto> findAll() {
        return this.studentRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDto> findAllByNrc(String nrc) {
        return this.studentRepository.findAllByNrc(nrc)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDto> findAllBySearching(Long regionId, String keyword) {
        List<Student> studentList;
        if(regionId == 0) {
            studentList = this.studentRepository.findAllByKeyword(keyword);
        } else {
            studentList = this.studentRepository.findAllByRegionAndKeyword(regionId, keyword);
        }
        return studentList
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long regionId, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Student> page = null;
        if(regionId == 0) {
            page = this.studentRepository.findAllByKeyword(keyword, sortedByCreatedTimestamp);
        } else {
            page = this.studentRepository.findAllByRegionAndKeyword(regionId, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentDto> res = new PaginationResponse<StudentDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public StudentDto save(StudentDto studentDto) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dob = inputDateFormat.parse(studentDto.getDob());
            studentDto.setDob(outputDateFormat.format(dob));
            studentDto.setRegDate(outputDateFormat.format(new Date()));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        return this.mapper.mapEntityToDto(this.studentRepository.save(this.mapper.mapDtoToEntity(studentDto)));
    }

    @Transactional
    @Override
    public StudentDto update(StudentDto studentDto, UUID id) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dob = inputDateFormat.parse(studentDto.getDob());
            studentDto.setDob(outputDateFormat.format(dob));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        studentDto.setId(id);
        return this.mapper.mapEntityToDto(this.studentRepository.save(this.mapper.mapDtoToEntity(studentDto)));
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        this.studentRepository.deleteById(id);
    }

}
