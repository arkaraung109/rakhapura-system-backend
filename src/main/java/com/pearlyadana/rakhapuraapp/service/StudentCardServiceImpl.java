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
    public PaginationResponse<StudentClassDto> findEachPageBySearchingSortByCreatedTimestamp(int pageNumber, boolean isAscending, Long examTitleId, Long academicYearId, Long gradeId, String studentClass, String keyword) {
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
            page = this.studentClassRepository.findAllByKeywordAndRegNoAndRegSeqNo(keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndKeywordAndRegNoAndRegSeqNo(examTitleId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndKeywordAndRegNoAndRegSeqNo(academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByGradeAndKeywordAndRegNoAndRegSeqNo(gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByClassAndKeywordAndRegNoAndRegSeqNo(studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndKeywordAndRegNoAndRegSeqNo(examTitleId, academicYearId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndKeywordAndRegNoAndRegSeqNo(examTitleId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndClassAndKeywordAndRegNoAndRegSeqNo(examTitleId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndKeywordAndRegNoAndRegSeqNo(academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndClassAndKeywordAndRegNoAndRegSeqNo(academicYearId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByGradeAndClassAndKeywordAndRegNoAndRegSeqNo(gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId != 0 && studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndKeywordAndRegNoAndRegSeqNo(examTitleId, academicYearId, gradeId, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId != 0 && gradeId == 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndClassAndKeywordAndRegNoAndRegSeqNo(examTitleId, academicYearId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId != 0 && academicYearId == 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByExamTitleAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(examTitleId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else if(examTitleId == 0 && academicYearId != 0 && gradeId != 0 && !studentClass.equals("All")) {
            page = this.studentClassRepository.findAllByAcademicYearAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(academicYearId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        } else {
            page = this.studentClassRepository.findAllByExamTitleAndAcademicYearAndGradeAndClassAndKeywordAndRegNoAndRegSeqNo(examTitleId, academicYearId, gradeId, studentClass, keyword, sortedByCreatedTimestamp);
        }

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().filter(s -> { return s.isArrival() && s.getRegNo() == null && s.getRegSeqNo() == 0; }).map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

}
