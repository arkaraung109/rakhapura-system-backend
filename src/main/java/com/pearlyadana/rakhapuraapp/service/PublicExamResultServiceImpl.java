package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.StudentClass;
import com.pearlyadana.rakhapuraapp.mapper.PublicExamResultMapper;
import com.pearlyadana.rakhapuraapp.mapper.StudentClassMapper;
import com.pearlyadana.rakhapuraapp.model.request.PublicExamResultDto;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.PublicExamResultRepository;
import com.pearlyadana.rakhapuraapp.repository.StudentClassRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicExamResultServiceImpl implements PublicExamResultService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private PublicExamResultRepository publicExamResultRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final StudentClassMapper studentClassMapper = Mappers.getMapper(StudentClassMapper.class);

    private final PublicExamResultMapper publicExamResultMapper = Mappers.getMapper(PublicExamResultMapper.class);

    @Transactional(readOnly = true)
    @Override
    public Integer findMaxSerialNo() {
        Integer maxSerialNo = this.publicExamResultRepository.findMaxSerialNo();
        if(maxSerialNo == null) {
            maxSerialNo = 0;
        }
        return maxSerialNo;
    }

    @Override
    public List<StudentClassDto> findPassedAllByAcademicYearAndExamTitleAndGrade(Long academicYearId, Long examTitleId, Long gradeId) {
        return this.studentClassRepository.findPassedAllByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId)
                .stream()
                .map(this.studentClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaginationResponse<StudentClassDto> findEachPageBySearching(int pageNumber, Long academicYearId, Long examTitleId, Long gradeId, String keyword) {
        Pageable pageable = PageRequest.of(PaginationUtil.pageNumber(pageNumber), paginationUtil.getPageSize());
        Page<StudentClass> page = this.studentClassRepository.findPassedAllByAcademicYearAndExamTitleAndGrade(academicYearId, examTitleId, gradeId, keyword, pageable);

        PaginationResponse<StudentClassDto> res = new PaginationResponse<StudentClassDto>();
        res.addList(page.stream().map(this.studentClassMapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public PublicExamResultDto save(PublicExamResultDto publicExamResultDto) {
        return this.publicExamResultMapper.mapEntityToDto(this.publicExamResultRepository.save(this.publicExamResultMapper.mapDtoToEntity(publicExamResultDto)));
    }


}
