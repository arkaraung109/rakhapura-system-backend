package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Subject;
import com.pearlyadana.rakhapuraapp.mapper.SubjectMapper;
import com.pearlyadana.rakhapuraapp.model.request.SubjectDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.SubjectRepository;
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
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final SubjectMapper mapper = Mappers.getMapper(SubjectMapper.class);

    @Transactional(readOnly = true)
    @Override
    public SubjectDto findById(Long id) {
        Optional<Subject> optional = this.subjectRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectDto> findAll() {
        return this.subjectRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubjectDto> findAllByName(String name) {
        return this.subjectRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.subjectRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<SubjectDto> findEachPageSortById(int pageNumber, boolean isAscending) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Subject> page = this.subjectRepository.findAll(sortedById);
        PaginationResponse<SubjectDto> res = new PaginationResponse<SubjectDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Override
    public PaginationResponse<SubjectDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Subject> page = this.subjectRepository.findAllByNameStartingWithIgnoreCase(keyword, sortedById);
        PaginationResponse<SubjectDto> res = new PaginationResponse<SubjectDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        subjectDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.subjectRepository.save(this.mapper.mapDtoToEntity(subjectDto)));
    }

    @Transactional
    @Override
    public SubjectDto update(SubjectDto subjectDto, Long id) {
        subjectDto.setId(id);
        return this.mapper.mapEntityToDto(this.subjectRepository.save(this.mapper.mapDtoToEntity(subjectDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.subjectRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.subjectRepository.authorizeById(id, authorizedUserId);
    }

}
