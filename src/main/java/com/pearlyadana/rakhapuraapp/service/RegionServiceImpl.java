package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Region;
import com.pearlyadana.rakhapuraapp.mapper.RegionMapper;
import com.pearlyadana.rakhapuraapp.model.request.RegionDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.RegionRepository;
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
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final RegionMapper mapper = Mappers.getMapper(RegionMapper.class);

    @Transactional(readOnly = true)
    @Override
    public RegionDto findById(Long id) {
        Optional<Region> optional = this.regionRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RegionDto> findAll() {
        return this.regionRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<RegionDto> findAllByName(String name) {
        return this.regionRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<RegionDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.regionRepository.findAllByAuthorizedStatusOrderByName(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<RegionDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Region> page = this.regionRepository.findAllByNameStartingWithIgnoreCase(keyword, sortedById);
        PaginationResponse<RegionDto> res = new PaginationResponse<RegionDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public RegionDto save(RegionDto regionDto) {
        regionDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.regionRepository.save(this.mapper.mapDtoToEntity(regionDto)));
    }

    @Transactional
    @Override
    public RegionDto update(RegionDto regionDto, Long id) {
        regionDto.setId(id);
        return this.mapper.mapEntityToDto(this.regionRepository.save(this.mapper.mapDtoToEntity(regionDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.regionRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.regionRepository.authorizeById(id, authorizedUserId);
    }

}
