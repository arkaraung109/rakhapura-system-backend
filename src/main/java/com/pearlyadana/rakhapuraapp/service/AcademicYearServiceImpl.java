package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.AcademicYear;
import com.pearlyadana.rakhapuraapp.mapper.AcademicYearMapper;
import com.pearlyadana.rakhapuraapp.model.request.AcademicYearDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.AcademicYearRepository;
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
public class AcademicYearServiceImpl implements AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final AcademicYearMapper mapper = Mappers.getMapper(AcademicYearMapper.class);

    @Transactional(readOnly = true)
    @Override
    public AcademicYearDto findById(Long id) {
        Optional<AcademicYear> optional = this.academicYearRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AcademicYearDto> findAll() {
        return this.academicYearRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AcademicYearDto> findAllByName(String name) {
        return this.academicYearRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AcademicYearDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.academicYearRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AcademicYearDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<AcademicYear> page = this.academicYearRepository.findAllByNameStartingWithIgnoreCase(keyword, sortedById);
        PaginationResponse<AcademicYearDto> res = new PaginationResponse<AcademicYearDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public AcademicYearDto save(AcademicYearDto academicYearDto) {
        academicYearDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.academicYearRepository.save(this.mapper.mapDtoToEntity(academicYearDto)));
    }

    @Transactional
    @Override
    public AcademicYearDto update(AcademicYearDto academicYearDto, Long id) {
        academicYearDto.setId(id);
        return this.mapper.mapEntityToDto(this.academicYearRepository.save(this.mapper.mapDtoToEntity(academicYearDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.academicYearRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.academicYearRepository.authorizeById(id, authorizedUserId);
    }

}
