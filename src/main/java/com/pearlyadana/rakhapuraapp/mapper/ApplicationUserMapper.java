package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.ApplicationUser;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper {

    ApplicationUserDto mapEntityToDto(ApplicationUser entity);

    ApplicationUser mapDtoToEntity(ApplicationUserDto dto);

}
