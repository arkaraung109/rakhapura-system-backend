package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Subject;
import com.pearlyadana.rakhapuraapp.model.request.SubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectDto mapEntityToDto(Subject entity);

    Subject mapDtoToEntity(SubjectDto dto);

}
