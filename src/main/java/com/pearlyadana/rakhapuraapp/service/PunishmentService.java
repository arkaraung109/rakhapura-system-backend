package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.PunishmentDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.UUID;

public interface PunishmentService {

    PunishmentDto findById(Long id);

    List<PunishmentDto> findAll();

    List<PunishmentDto> findAllBySearching(String keyword);

    List<PunishmentDto> findAllByPunishmentAndEventDateAndStudent(String punishment, String eventDate, UUID studentId);

    PaginationResponse<PunishmentDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    PunishmentDto save(PunishmentDto punishmentDto);

    PunishmentDto update(PunishmentDto punishmentDto, Long id);

    void deleteById(Long id);

}
