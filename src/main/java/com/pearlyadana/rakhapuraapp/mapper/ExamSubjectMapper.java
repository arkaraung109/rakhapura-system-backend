package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.ExamSubject;
import com.pearlyadana.rakhapuraapp.model.request.ExamSubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamSubjectMapper {

    ExamSubjectDto mapEntityToDto(ExamSubject entity);

    ExamSubject mapDtoToEntity(ExamSubjectDto dto);

}
