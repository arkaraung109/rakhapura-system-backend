package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.mapper.UserRoleMapper;
import com.pearlyadana.rakhapuraapp.model.response.UserRoleDto;
import com.pearlyadana.rakhapuraapp.repository.UserRoleRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRoleMapper mapper = Mappers.getMapper(UserRoleMapper.class);

    @Override
    public List<UserRoleDto> findAll() {
        return this.userRoleRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
