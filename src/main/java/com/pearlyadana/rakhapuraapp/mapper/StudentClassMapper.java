package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.StudentClass;
import com.pearlyadana.rakhapuraapp.model.request.StudentClassDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentClassMapper {

    StudentClassDto mapEntityToDto(StudentClass entity);

    StudentClass mapDtoToEntity(StudentClassDto dto);

}
