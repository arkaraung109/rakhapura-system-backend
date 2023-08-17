package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Grade;
import com.pearlyadana.rakhapuraapp.mapper.GradeMapper;
import com.pearlyadana.rakhapuraapp.model.request.GradeDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.GradeRepository;
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
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final GradeMapper mapper = Mappers.getMapper(GradeMapper.class);

    @Transactional(readOnly = true)
    @Override
    public GradeDto findById(Long id) {
        Optional<Grade> optional = this.gradeRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GradeDto> findAll() {
        return this.gradeRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GradeDto> findAllByName(String name) {
        return this.gradeRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GradeDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.gradeRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<GradeDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Grade> page = this.gradeRepository.findAllByKeyword(keyword, sortedById);
        PaginationResponse<GradeDto> res = new PaginationResponse<GradeDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public GradeDto save(GradeDto gradeDto) {
        gradeDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.gradeRepository.save(this.mapper.mapDtoToEntity(gradeDto)));
    }

    @Transactional
    @Override
    public GradeDto update(GradeDto gradeDto, Long id) {
        gradeDto.setId(id);
        return this.mapper.mapEntityToDto(this.gradeRepository.save(this.mapper.mapDtoToEntity(gradeDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.gradeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.gradeRepository.authorizeById(id, authorizedUserId);
    }

}
