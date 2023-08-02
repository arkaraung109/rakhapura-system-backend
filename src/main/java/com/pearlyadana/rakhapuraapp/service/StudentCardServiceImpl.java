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
public class StudentCardServiceImpl implements StudentCardService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentClassMapper mapper = Mappers.getMapper(StudentClassMapper.class);

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId) {
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
            page = this.studentClassRepository.findAllForStudentCard(sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleForStudentCard(examTitleId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByAcademicYearForStudentCard(academicYearId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByGradeForStudentCard(gradeId, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearForStudentCard(examTitleId, academicYearId, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeForStudentCard(examTitleId, gradeId, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeForStudentCard(academicYearId, gradeId, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeForStudentCard(examTitleId, academicYearId, gradeId, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

}
