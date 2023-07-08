package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;

import java.util.Optional;

public interface UserTableService {

    Optional<ApplicationUserDto> findUserTableByLoginUsername(String userName);

}
