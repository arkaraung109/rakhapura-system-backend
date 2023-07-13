package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Attendance;
import com.pearlyadana.rakhapuraapp.mapper.AttendanceMapper;
import com.pearlyadana.rakhapuraapp.model.request.AcademicYearDto;
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

    @Transactional(readOnly = true)
    @Override
    public List<AttendanceDto> findAll() {
        return this.attendanceRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean present) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<Attendance> page = this.attendanceRepository.findAllByPresent(present, sortedByCreatedTimestamp);
        PaginationResponse<AttendanceDto> res = new PaginationResponse<AttendanceDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AttendanceDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean present, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword) {
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
            page = this.attendanceRepository.findAllByKeywordAndPresent(keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndKeywordAndPresent(academicYearId, keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByExamTitleAndKeywordAndPresent(examTitleId, keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllBySubjectTypeAndKeywordAndPresent(subjectTypeId, keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndExamTitleAndKeywordAndPresent(academicYearId, examTitleId, keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllByAcademicYearAndSubjectTypeAndKeywordAndPresent(academicYearId, subjectTypeId, keyword, present, sortedByCreatedTimestamp);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId != 0) {
            page = this.attendanceRepository.findAllByExamTitleAndSubjectTypeAndKeywordAndPresent(examTitleId, subjectTypeId, keyword, present, sortedByCreatedTimestamp);
        } else {
            page = this.attendanceRepository.findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeywordAndPresent(academicYearId, examTitleId, subjectTypeId, keyword, present, sortedByCreatedTimestamp);
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
