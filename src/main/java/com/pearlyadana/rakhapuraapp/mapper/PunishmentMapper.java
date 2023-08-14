package com.pearlyadana.rakhapuraapp.mapper;

import com.pearlyadana.rakhapuraapp.entity.Punishment;
import com.pearlyadana.rakhapuraapp.model.request.PunishmentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PunishmentMapper {

    PunishmentDto mapEntityToDto(Punishment entity);

    Punishment mapDtoToEntity(PunishmentDto dto);

}
