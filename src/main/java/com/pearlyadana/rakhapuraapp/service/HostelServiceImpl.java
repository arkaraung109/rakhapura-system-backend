package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Hostel;
import com.pearlyadana.rakhapuraapp.mapper.HostelMapper;
import com.pearlyadana.rakhapuraapp.model.request.HostelDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.HostelRepository;
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
public class HostelServiceImpl implements HostelService {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final HostelMapper mapper = Mappers.getMapper(HostelMapper.class);

    @Transactional(readOnly = true)
    @Override
    public HostelDto findById(Long id) {
        Optional<Hostel> optional = this.hostelRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HostelDto> findAll() {
        return this.hostelRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<HostelDto> findAllByName(String name) {
        return this.hostelRepository.findAllByName(name)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<HostelDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.hostelRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<HostelDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Hostel> page = this.hostelRepository.findAllByKeyword(keyword, sortedById);
        PaginationResponse<HostelDto> res = new PaginationResponse<HostelDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public HostelDto save(HostelDto hostelDto) {
        hostelDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.hostelRepository.save(this.mapper.mapDtoToEntity(hostelDto)));
    }

    @Transactional
    @Override
    public HostelDto update(HostelDto hostelDto, Long id) {
        hostelDto.setId(id);
        return this.mapper.mapEntityToDto(this.hostelRepository.save(this.mapper.mapDtoToEntity(hostelDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.hostelRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.hostelRepository.authorizeById(id, authorizedUserId);
    }

}
