package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.ExamTitle;
import com.pearlyadana.rakhapuraapp.model.request.ExamTitleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamTitleMapper {

    ExamTitleDto mapEntityToDto(ExamTitle entity);

    ExamTitle mapDtoToEntity(ExamTitleDto dto);

}
