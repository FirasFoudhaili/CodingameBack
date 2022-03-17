package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.HistoryDto;
import com.byblos.evaluation.evaluationservice.models.History;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface HistoryMapper {

    HistoryDto toHistoryDto(History history);

    List<HistoryDto> toHistoryDtos(List<History> histories);

    History toHistory(HistoryDto historyDto);
}

