package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.SubjectType;
import com.pearlyadana.rakhapuraapp.mapper.SubjectTypeMapper;
import com.pearlyadana.rakhapuraapp.model.request.SubjectTypeDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.SubjectTypeRepository;
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
public class SubjectTypeServiceImpl implements SubjectTypeService {

    @Autowired
    private SubjectTypeRepository subjectTypeRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final SubjectTypeMapper mapper = Mappers.getMapper(SubjectTypeMapper.class);

    @Transactional(readOnly = true)
    @Override
    public SubjectTypeDto findById(Long id) {
        Optional<SubjectType> optional = this.subjectTypeRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectTypeDto> findAll() {
        return this.subjectTypeRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectTypeDto> findAllByNameAndGrade(String name, Long gradeId) {
        return this.subjectTypeRepository.findAllByNameAndGradeId(name, gradeId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectTypeDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.subjectTypeRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectTypeDto> findAllFilteredByGrade(Long gradeId) {
        return this.subjectTypeRepository.findAllByGradeIdAndAuthorizedStatus(gradeId, true)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<SubjectTypeDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long gradeId, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<SubjectType> page = null;
        if(gradeId == 0) {
            page = this.subjectTypeRepository.findAllByKeyword(keyword, sortedById);
        } else {
            page = this.subjectTypeRepository.findAllByGradeAndKeyword(gradeId, keyword, sortedById);
        }

        PaginationResponse<SubjectTypeDto> res = new PaginationResponse<SubjectTypeDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public SubjectTypeDto save(SubjectTypeDto subjectTypeDto) {
        subjectTypeDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.subjectTypeRepository.save(this.mapper.mapDtoToEntity(subjectTypeDto)));
    }

    @Transactional
    @Override
    public SubjectTypeDto update(SubjectTypeDto subjectTypeDto, Long id) {
        subjectTypeDto.setId(id);
        return this.mapper.mapEntityToDto(this.subjectTypeRepository.save(this.mapper.mapDtoToEntity(subjectTypeDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.subjectTypeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.subjectTypeRepository.authorizeById(id, authorizedUserId);
    }

}
