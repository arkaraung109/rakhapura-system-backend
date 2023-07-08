package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.AcademicYear;
import com.pearlyadana.rakhapuraapp.model.request.AcademicYearDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcademicYearMapper {

    AcademicYearDto mapEntityToDto(AcademicYear entity);

    AcademicYear mapDtoToEntity(AcademicYearDto dto);

}
