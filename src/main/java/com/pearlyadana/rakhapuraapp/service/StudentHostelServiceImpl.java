package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.StudentClass;
import com.pearlyadana.rakhapuraapp.mapper.StudentClassMapper;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.StudentClassRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class StudentHostelServiceImpl implements StudentHostelService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentClassMapper mapper = Mappers.getMapper(StudentClassMapper.class);

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachNotPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = this.studentClassRepository.findAllByHostelNotPresent(sortedByCreatedTimestamp);
        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Override
    public PaginationResponse<StudentClassDto> findEachNotPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = null;
        if(examTitleId == 0 && academicYearId == 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByKeywordAndHostelNotPresent(keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndKeywordAndHostelNotPresent(examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndKeywordAndHostelNotPresent(academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByGradeAndKeywordAndHostelNotPresent(gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndKeywordAndHostelNotPresent(examTitleId, academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndKeywordAndHostelNotPresent(examTitleId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndKeywordAndHostelNotPresent(academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndKeyword(examTitleId, academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachPresentPageSortByCreatedTimestamp(int pageNumber, boolean isAscending) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = this.studentClassRepository.findAllByHostelPresent(sortedByCreatedTimestamp);
        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Override
    public PaginationResponse<StudentClassDto> findEachPresentPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, Long hostelId, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = null;
        if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByKeywordAndHostelPresent(keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndKeywordAndHostelPresent(examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndKeywordAndHostelPresent(academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByGradeAndKeywordAndHostelPresent(gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByHostelAndKeywordAndHostelPresent(hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndKeywordAndHostelPresent(examTitleId, academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndKeywordAndHostelPresent(examTitleId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndHostelAndKeywordAndHostelPresent(examTitleId, hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndKeywordAndHostelPresent(academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndHostelAndKeywordAndHostelPresent(academicYearId, hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByGradeAndHostelAndKeywordAndHostelPresent(gradeId, hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId != 0 && hostelId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndHostelPresent(examTitleId, academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndHostelAndKeywordAndHostelPresent(examTitleId, academicYearId, hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndHostelAndKeywordAndHostelPresent(examTitleId, gradeId, hostelId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && hostelId != 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(academicYearId, gradeId, hostelId, keyword, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndHostelAndKeywordAndHostelPresent(examTitleId, academicYearId, gradeId, hostelId, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

}
