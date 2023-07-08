package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.RegionDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface RegionService {

    RegionDto findById(Long id);

    List<RegionDto> findAll();

    List<RegionDto> findAllByName(String name);

    List<RegionDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<RegionDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<RegionDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    RegionDto save(RegionDto regionDto);

    RegionDto update(RegionDto regionDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
