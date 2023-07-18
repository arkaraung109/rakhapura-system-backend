package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Attendance;
import com.pearlyadana.rakhapuraapp.mapper.AttendanceMapper;
import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.AttendanceRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final AttendanceMapper mapper = Mappers.getMapper(AttendanceMapper.class);

    @Transactional(readOnly = true)
    @Override
    public AttendanceDto findById(UUID id) {
        Optional<Attendance> optional = this.attendanceRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Override
    public List<AttendanceDto> findByStudentClassId(UUID id) {
        return this.attendanceRepository.findByStudentClassId(id)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AttendanceDto> findAll() {
        return this.attendanceRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDto> findByOrderByCreatedTimestampAsc() {
        return this.attendanceRepository.findByOrderByCreatedTimestampAsc()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachNotPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Attendance> page = this.attendanceRepository.findAllByNotPresent(sortedByCreatedTimestamp);
        PaginationResponse<AttendanceDto> res = new PaginationResponse<AttendanceDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachNotPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Attendance> page = null;
        if(academicYearId == 0 && examTitleId == 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByKeywordAndNotPresent(keyword, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndKeywordAndNotPresent(academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByExamTitleAndKeywordAndNotPresent(examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllBySubjectTypeAndKeywordAndNotPresent(subjectTypeId, keyword, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndExamTitleAndKeywordAndNotPresent(academicYearId, examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndSubjectTypeAndKeywordAndNotPresent(academicYearId, subjectTypeId, keyword, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllByExamTitleAndSubjectTypeAndKeywordAndNotPresent(examTitleId, subjectTypeId, keyword, sortedByCreatedTimestamp);
        } else {
            page = this.attendanceRepository.findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeywordAndNotPresent(academicYearId, examTitleId, subjectTypeId, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<AttendanceDto> res = new PaginationResponse<AttendanceDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Attendance> page = this.attendanceRepository.findAllByPresent(sortedByCreatedTimestamp);
        PaginationResponse<AttendanceDto> res = new PaginationResponse<AttendanceDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long gradeId, String studentClass, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Attendance> page = null;
        if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByKeywordAndPresent(keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndKeywordAndPresent(examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByAcademicYearAndKeywordAndPresent(academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByGradeAndKeywordAndPresent(gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByClassAndKeywordAndPresent(studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndAcademicYearAndKeywordAndPresent(examTitleId, academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndGradeAndKeywordAndPresent(examTitleId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndClassAndKeywordAndPresent(examTitleId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByAcademicYearAndGradeAndKeywordAndPresent(academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByAcademicYearAndClassAndKeywordAndPresent(academicYearId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByGradeAndClassAndKeywordAndPresent(gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndPresent(examTitleId, academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndAcademicYearAndClassAndKeywordAndPresent(examTitleId, academicYearId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByExamTitleAndGradeAndClassAndKeywordAndPresent(examTitleId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.attendanceRepository.findAllByAcademicYearAndGradeAndClassAndKeywordAndPresent(academicYearId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else {
            page = this.attendanceRepository.findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndPresent(examTitleId, academicYearId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<AttendanceDto> res = new PaginationResponse<AttendanceDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public AttendanceDto save(AttendanceDto attendanceDto) {
        return this.mapper.mapEntityToDto(this.attendanceRepository.save(this.mapper.mapDtoToEntity(attendanceDto)));
    }

    @Transactional
    @Override
    public AttendanceDto update(AttendanceDto attendanceDto, UUID id) {
        attendanceDto.setId(id);
        return this.mapper.mapEntityToDto(this.attendanceRepository.save(this.mapper.mapDtoToEntity(attendanceDto)));
    }

}
