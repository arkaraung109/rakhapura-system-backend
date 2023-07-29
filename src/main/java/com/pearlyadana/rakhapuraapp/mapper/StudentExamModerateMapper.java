package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.StudentExamModerate;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamModerateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentExamModerateMapper {

    StudentExamModerateDto mapEntityToDto(StudentExamModerate entity);

    StudentExamModerate mapDtoToEntity(StudentExamModerateDto dto);

}
