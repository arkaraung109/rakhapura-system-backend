package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Award;
import com.pearlyadana.rakhapuraapp.model.request.AwardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    AwardDto mapEntityToDto(Award entity);

    Award mapDtoToEntity(AwardDto dto);

}
