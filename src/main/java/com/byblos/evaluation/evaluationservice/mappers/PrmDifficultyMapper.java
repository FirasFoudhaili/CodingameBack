package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.PrmDifficultyDto;
import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrmDifficultyMapper {

    PrmDifficultyDto toPrmDifficultyDto(PrmDifficulty prmDifficulty);

    List<PrmDifficultyDto> toPrmDifficultyDtos(List<PrmDifficulty> prmDifficulties);

    PrmDifficulty toPrmDifficulty(PrmDifficultyDto prmDifficultyDto);
}


