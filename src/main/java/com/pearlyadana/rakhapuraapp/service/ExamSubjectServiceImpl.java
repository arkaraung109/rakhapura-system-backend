package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.ExamSubject;
import com.pearlyadana.rakhapuraapp.mapper.ExamSubjectMapper;
import com.pearlyadana.rakhapuraapp.model.request.ExamSubjectDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.ExamSubjectRepository;
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
import java.util.stream.Collectors;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

    @Autowired
    private ExamSubjectRepository examSubjectRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final ExamSubjectMapper mapper = Mappers.getMapper(ExamSubjectMapper.class);

    @Transactional(readOnly = true)
    @Override
    public ExamSubjectDto findById(Long id) {
        Optional<ExamSubject> optional = this.examSubjectRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamSubjectDto> findAll() {
        return this.examSubjectRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamSubjectDto> findAllByExam(Long examId) {
        return this.examSubjectRepository.findAllByExamId(examId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamSubjectDto> findAllAuthorizedByExam(Long examId) {
        return this.examSubjectRepository.findAllByExamIdAndAuthorizedStatusOrderBySubjectIdAsc(examId, true)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamSubjectDto> findAllByExamAndSubject(Long examId, Long subjectId) {
        return this.examSubjectRepository.findAllByExamIdAndSubjectId(examId, subjectId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamSubjectDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.examSubjectRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<ExamSubjectDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long examTitleId, Long subjectTypeId, Long subjectId, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<ExamSubject> page = null;
        if(academicYearId == 0 && examTitleId == 0 && subjectTypeId == 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByKeyword(keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId == 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndKeyword(academicYearId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId == 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByExamTitleAndKeyword(examTitleId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId != 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllBySubjectTypeAndKeyword(subjectTypeId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId == 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllBySubjectAndKeyword(subjectId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId == 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndExamTitleAndKeyword(academicYearId, examTitleId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId != 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndSubjectTypeAndKeyword(academicYearId, subjectTypeId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId == 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndSubjectAndKeyword(academicYearId, subjectId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId != 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByExamTitleAndSubjectTypeAndKeyword(examTitleId, subjectTypeId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId == 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllByExamTitleAndSubjectAndKeyword(examTitleId, subjectId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId == 0 && subjectTypeId != 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllBySubjectTypeAndSubjectAndKeyword(subjectTypeId, subjectId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId != 0 && subjectId == 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndExamTitleAndSubjectTypeAndKeyword(academicYearId, examTitleId, subjectTypeId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId != 0 && subjectTypeId == 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndExamTitleAndSubjectAndKeyword(academicYearId, examTitleId, subjectId, keyword, sortedById);
        } else if(academicYearId != 0 && examTitleId == 0 && subjectTypeId != 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllByAcademicYearAndSubjectTypeAndSubjectAndKeyword(academicYearId, subjectTypeId, subjectId, keyword, sortedById);
        } else if(academicYearId == 0 && examTitleId != 0 && subjectTypeId != 0 && subjectId != 0) {
            page = this.examSubjectRepository.findAllByExamTitleAndSubjectTypeAndSubjectAndKeyword(examTitleId, subjectTypeId, subjectId, keyword, sortedById);
        } else {
            page = this.examSubjectRepository.findAllByAcademicYearAndExamTitleAndSubjectTypeAndSubjectAndKeyword(academicYearId, examTitleId, subjectTypeId, subjectId, keyword, sortedById);
        }

        PaginationResponse<ExamSubjectDto> res = new PaginationResponse<ExamSubjectDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public ExamSubjectDto save(ExamSubjectDto examSubjectDto) {
        return this.mapper.mapEntityToDto(this.examSubjectRepository.save(this.mapper.mapDtoToEntity(examSubjectDto)));
    }

    @Transactional
    @Override
    public ExamSubjectDto update(ExamSubjectDto examSubjectDto, Long id) {
        examSubjectDto.setId(id);
        return this.mapper.mapEntityToDto(this.examSubjectRepository.save(this.mapper.mapDtoToEntity(examSubjectDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.examSubjectRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.examSubjectRepository.authorizeById(id, authorizedUserId);
    }

}
