package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Punishment;
import com.pearlyadana.rakhapuraapp.mapper.PunishmentMapper;
import com.pearlyadana.rakhapuraapp.model.request.PunishmentDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.PunishmentRepository;
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
public class PunishmentServiceImpl implements PunishmentService {

    @Autowired
    private PunishmentRepository punishmentRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final PunishmentMapper mapper = Mappers.getMapper(PunishmentMapper.class);

    @Transactional(readOnly = true)
    @Override
    public PunishmentDto findById(Long id) {
        Optional<Punishment> optional = this.punishmentRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PunishmentDto> findAll() {
        return this.punishmentRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PunishmentDto> findAllBySearching(String keyword) {
        return this.punishmentRepository.findAllBySearching(keyword)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PunishmentDto> findAllByPunishmentAndEventDateAndStudent(String punishment, String eventDate, UUID studentId) {
        return this.punishmentRepository.findAllByPunishmentAndEventDateAndStudentId(punishment, eventDate, studentId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<PunishmentDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Punishment> page = this.punishmentRepository.findAllByKeyword(keyword, sortedById);
        PaginationResponse<PunishmentDto> res = new PaginationResponse<PunishmentDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public PunishmentDto save(PunishmentDto punishmentDto) {
        return this.mapper.mapEntityToDto(this.punishmentRepository.save(this.mapper.mapDtoToEntity(punishmentDto)));
    }

    @Transactional
    @Override
    public PunishmentDto update(PunishmentDto punishmentDto, Long id) {
        punishmentDto.setId(id);
        return this.mapper.mapEntityToDto(this.punishmentRepository.save(this.mapper.mapDtoToEntity(punishmentDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.punishmentRepository.deleteById(id);
    }

}
