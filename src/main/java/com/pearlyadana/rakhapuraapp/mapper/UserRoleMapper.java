package com.pearlyadana.rakhapuraapp.mapper;


import com.pearlyadana.rakhapuraapp.entity.UserRole;
import com.pearlyadana.rakhapuraapp.model.response.UserRoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRoleDto mapEntityToDto(UserRole entity);

    UserRole mapDtoToEntity(UserRoleDto dto);

}
