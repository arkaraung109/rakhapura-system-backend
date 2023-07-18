package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.StudentExam;
import com.pearlyadana.rakhapuraapp.model.request.StudentExamDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentExamMapper {

    StudentExamDto mapEntityToDto(StudentExam entity);

    StudentExam mapDtoToEntity(StudentExamDto dto);

}
