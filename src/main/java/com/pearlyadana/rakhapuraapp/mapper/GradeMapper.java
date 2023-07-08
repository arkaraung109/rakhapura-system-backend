package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Grade;
import com.pearlyadana.rakhapuraapp.model.request.GradeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    GradeDto mapEntityToDto(Grade entity);

    Grade mapDtoToEntity(GradeDto dto);

}
