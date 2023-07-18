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
public class ArrivalServiceImpl implements ArrivalService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentClassMapper mapper = Mappers.getMapper(StudentClassMapper.class);

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachPageSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean arrival) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = this.studentClassRepository.findAllByArrival(arrival, sortedByCreatedTimestamp);
        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, boolean arrival, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword) {
        Pageable sortedByCreatedTimestamp = null;
        if(isAscending) {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").ascending());
        } else {
            sortedByCreatedTimestamp = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("createdTimestamp").descending());
        }
        Page<StudentClass> page = null;
        if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByKeywordAndArrival(keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndKeywordAndArrival(examTitleId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndKeywordAndArrival(academicYearId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByGradeAndKeywordAndArrival(gradeId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByClassAndKeywordAndArrival(studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndKeywordAndArrival(examTitleId, academicYearId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndKeywordAndArrival(examTitleId, gradeId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndClassAndKeywordAndArrival(examTitleId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndKeywordAndArrival(academicYearId, gradeId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndClassAndKeywordAndArrival(academicYearId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByGradeAndClassAndKeywordAndArrival(gradeId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndArrival(examTitleId, academicYearId, gradeId, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndClassAndKeywordAndArrival(examTitleId, academicYearId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndClassAndKeywordAndArrival(examTitleId, gradeId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndClassAndKeywordAndArrival(academicYearId, gradeId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndArrival(examTitleId, academicYearId, gradeId, studentClass, keyword, arrival, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

}
