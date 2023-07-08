package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Exam;
import com.pearlyadana.rakhapuraapp.model.request.ExamDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    ExamDto mapEntityToDto(Exam entity);

    Exam mapDtoToEntity(ExamDto dto);

}
