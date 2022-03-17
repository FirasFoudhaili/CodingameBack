package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.ScoreParTechnologyDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParTechnology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ScoreParTechnologyMapper {
    ScoreParTechnologyDto toScoreParTechnologyDto(ScoreParTechnology scoreParTechnology);

    List<ScoreParTechnologyDto> toScoreParTechnologyDtos(List<ScoreParTechnology> scoreParTechnologies);

    ScoreParTechnology toScoreParTechnology(ScoreParTechnologyDto scoreParTechnologyDto);
}
