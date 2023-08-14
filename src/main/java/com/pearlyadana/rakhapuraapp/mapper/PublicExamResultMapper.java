package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.PublicExamResult;
import com.pearlyadana.rakhapuraapp.model.request.PublicExamResultDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicExamResultMapper {

    PublicExamResultDto mapEntityToDto(PublicExamResult entity);

    PublicExamResult mapDtoToEntity(PublicExamResultDto dto);

}
