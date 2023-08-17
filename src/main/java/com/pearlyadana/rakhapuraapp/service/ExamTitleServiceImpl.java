package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.ExamTitle;
import com.pearlyadana.rakhapuraapp.mapper.ExamTitleMapper;
import com.pearlyadana.rakhapuraapp.model.request.ExamTitleDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.ExamTitleRepository;
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
public class ExamTitleServiceImpl implements ExamTitleService {

    @Autowired
    private ExamTitleRepository examTitleRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final ExamTitleMapper mapper = Mappers.getMapper(ExamTitleMapper.class);

    @Transactional(readOnly = true)
    @Override
    public ExamTitleDto findById(Long id) {
        Optional<ExamTitle> optional = this.examTitleRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamTitleDto> findAll() {
        return this.examTitleRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamTitleDto> findAllByName(String name) {
        return this.examTitleRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamTitleDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.examTitleRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<ExamTitleDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<ExamTitle> page = this.examTitleRepository.findAllByNameStartingWithIgnoreCase(keyword, sortedById);
        PaginationResponse<ExamTitleDto> res = new PaginationResponse<ExamTitleDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public ExamTitleDto save(ExamTitleDto examTitleDto) {
        examTitleDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.examTitleRepository.save(this.mapper.mapDtoToEntity(examTitleDto)));
    }

    @Transactional
    @Override
    public ExamTitleDto update(ExamTitleDto examTitleDto, Long id) {
        examTitleDto.setId(id);
        return this.mapper.mapEntityToDto(this.examTitleRepository.save(this.mapper.mapDtoToEntity(examTitleDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.examTitleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.examTitleRepository.authorizeById(id, authorizedUserId);
    }

}
