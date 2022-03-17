package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;
import com.byblos.evaluation.evaluationservice.models.PrmLevel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrmLevelMapper {

    PrmLevelDto toPrmLevelDto(PrmLevel prmLevel);

    List<PrmLevelDto> toPrmLevelDtos(List<PrmLevel> prmLevels);

    PrmLevel toPrmLevel(PrmLevelDto prmLevelDto);
}


