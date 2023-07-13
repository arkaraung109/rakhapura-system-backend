package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Attendance;
import com.pearlyadana.rakhapuraapp.model.request.AttendanceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceDto mapEntityToDto(Attendance entity);

    Attendance mapDtoToEntity(AttendanceDto dto);

}
