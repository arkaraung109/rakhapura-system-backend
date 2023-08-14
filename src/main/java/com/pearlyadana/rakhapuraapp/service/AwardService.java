package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.model.request.AwardDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;

import java.util.List;
import java.util.UUID;

public interface AwardService {

    AwardDto findById(Long id);

    List<AwardDto> findAll();

    List<AwardDto> findAllBySearching(String keyword);

    List<AwardDto> findAllByAwardAndEventDateAndStudent(String award, String eventDate, UUID studentId);

    PaginationResponse<AwardDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, String keyword);

    AwardDto save(AwardDto awardDto);

    AwardDto update(AwardDto awardDto, Long id);

    void deleteById(Long id);

}
