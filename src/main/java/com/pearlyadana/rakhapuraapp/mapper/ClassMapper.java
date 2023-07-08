package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Class;
import com.pearlyadana.rakhapuraapp.model.request.ClassDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    ClassDto mapEntityToDto(Class entity);

    Class mapDtoToEntity(ClassDto dto);

}
