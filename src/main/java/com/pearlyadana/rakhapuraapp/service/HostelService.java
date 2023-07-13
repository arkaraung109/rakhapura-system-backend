package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.HostelDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;

public interface HostelService {

    HostelDto findById(Long id);

    List<HostelDto> findAll();

    List<HostelDto> findAllByName(String name);

    List<HostelDto> findAllByAuthorizedStatus(boolean authorizedStatus);

    PaginationResponse<HostelDto> findEachPageSortById(int pageNumber, boolean isAscending);

    PaginationResponse<HostelDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    HostelDto save(HostelDto hostelDto);

    HostelDto update(HostelDto hostelDto, Long id);

    void deleteById(Long id);

    void authorizeById(Long id, Long authorizedUserId);

}
