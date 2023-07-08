package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.SubjectType;
import com.pearlyadana.rakhapuraapp.model.request.SubjectTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectTypeMapper {

    SubjectTypeDto mapEntityToDto(SubjectType entity);

    SubjectType mapDtoToEntity(SubjectTypeDto dto);

}
