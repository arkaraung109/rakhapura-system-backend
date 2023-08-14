package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Award;
import com.pearlyadana.rakhapuraapp.mapper.AwardMapper;
import com.pearlyadana.rakhapuraapp.model.request.AwardDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.AwardRepository;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final AwardMapper mapper = Mappers.getMapper(AwardMapper.class);

    @Transactional(readOnly = true)
    @Override
    public AwardDto findById(Long id) {
        Optional<Award> optional = this.awardRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AwardDto> findAll() {
        return this.awardRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AwardDto> findAllBySearching(String keyword) {
        return this.awardRepository.findAllBySearching(keyword)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AwardDto> findAllByAwardAndEventDateAndStudent(String award, String eventDate, UUID studentId) {
        return this.awardRepository.findAllByAwardAndEventDateAndStudentId(award, eventDate, studentId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<AwardDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Award> page = this.awardRepository.findAllByKeyword(keyword, sortedById);
        PaginationResponse<AwardDto> res = new PaginationResponse<AwardDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public AwardDto save(AwardDto awardDto) {
        return this.mapper.mapEntityToDto(this.awardRepository.save(this.mapper.mapDtoToEntity(awardDto)));
    }

    @Transactional
    @Override
    public AwardDto update(AwardDto awardDto, Long id) {
        awardDto.setId(id);
        return this.mapper.mapEntityToDto(this.awardRepository.save(this.mapper.mapDtoToEntity(awardDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.awardRepository.deleteById(id);
    }

}
