package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Hostel;
import com.pearlyadana.rakhapuraapp.model.request.HostelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HostelMapper {

    HostelDto mapEntityToDto(Hostel entity);

    Hostel mapDtoToEntity(HostelDto dto);

}
