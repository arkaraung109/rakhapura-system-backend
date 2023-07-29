package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Exam;
import com.pearlyadana.rakhapuraapp.mapper.ExamMapper;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.ExamRepository;
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
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final ExamMapper mapper = Mappers.getMapper(ExamMapper.class);

    @Transactional(readOnly = true)
    @Override
    public ExamDto findById(Long id) {
        Optional<Exam> optional = this.examRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public ExamDto findByAcademicYearAndExamTitleAndSubjectType(Long academicYearId, Long examTitleId, Long subjectTypeId) {
        Optional<Exam> optional = this.examRepository.findFirstByAcademicYearIdAndExamTitleIdAndSubjectTypeId(academicYearId, examTitleId, subjectTypeId);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamDto> findAllFilteredByAcademicYearAndExamTitle(Long academicYearId, Long examTitleId) {
        return this.examRepository.findAllByAcademicYearIdAndExamTitleIdAndAuthorizedStatus(academicYearId, examTitleId, true)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamDto> findAllFilteredByAcademicYearAndExamTitleAndGrade(Long academicYearId, Long examTitleId, Long gradeId) {
        return this.examRepository.findAllByAcademicYearIdAndExamTitleIdAndGradeIdAndAuthorizedStatus(academicYearId, examTitleId, gradeId, true)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamDto> findAll() {
        return this.examRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamDto> findAllByAcademicYearAndExamTitleAndSubjectType(Long academicYearId, Long examTitleId, Long subjectTypeId) {
        return this.examRepository.findAllByAcademicYearIdAndExamTitleIdAndSubjectTypeId(academicYearId, examTitleId, subjectTypeId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<ExamDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Exam> page = null;
        if(academicYearId == 0 && examTitleId == 0 && subjectTypeId == 0) {
            page = this.examRepository.findAllByKeyword(keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId == 0) {
            page = this.examRepository.findAllByAcademicYearAndKeyword(academicYearId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.examRepository.findAllByExamTitleAndKeyword(examTitleId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.examRepository.findAllBySubjectTypeAndKeyword(subjectTypeId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId == 0) {
            page = this.examRepository.findAllByAcademicYearAndExamTitleAndKeyword(academicYearId, examTitleId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId != 0) {
            page = this.examRepository.findAllByAcademicYearAndSubjectTypeAndKeyword(academicYearId, subjectTypeId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId != 0) {
            page = this.examRepository.findAllByExamTitleAndSubjectTypeAndKeyword(examTitleId, subjectTypeId, keyword, sortedById);
        } else {
            page = this.examRepository.findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeyword(academicYearId, examTitleId, subjectTypeId, keyword, sortedById);
        }

        PaginationResponse<ExamDto> res = new PaginationResponse<ExamDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public ExamDto save(ExamDto examDto) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date examDate = inputDateFormat.parse(examDto.getExamDate());
            examDto.setExamDate(outputDateFormat.format(examDate));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        examDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.examRepository.save(this.mapper.mapDtoToEntity(examDto)));
    }

    @Transactional
    @Override
    public ExamDto update(ExamDto examDto, Long id) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date examDate = inputDateFormat.parse(examDto.getExamDate());
            examDto.setExamDate(outputDateFormat.format(examDate));
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }
        examDto.setId(id);
        return this.mapper.mapEntityToDto(this.examRepository.save(this.mapper.mapDtoToEntity(examDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.examRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.examRepository.authorizeById(id, authorizedUserId);
    }

}
