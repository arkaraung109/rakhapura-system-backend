package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface UserTableService {

    ApplicationUserDto findById(Long id);

    List<ApplicationUserDto> findAllBySearching(Long roleId, String keyword);

    PaginationResponse<ApplicationUserDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long roleId, String keyword);

    Optional<ApplicationUserDto> findUserTableByLoginUsername(String userName);

    ApplicationUserDto save(ApplicationUserDto applicationUserDto);

    ApplicationUserDto update(ApplicationUserDto body, Long id);

}
