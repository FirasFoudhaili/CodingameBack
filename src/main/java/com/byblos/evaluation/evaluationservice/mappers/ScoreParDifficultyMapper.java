package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.ScoreParDifficultyDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParDifficulty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ScoreParDifficultyMapper {
    ScoreParDifficultyDto toScoreParDifficultyDto(ScoreParDifficulty scoreParDifficulty);

    List<ScoreParDifficultyDto> toScoreParDifficultyDtos(List<ScoreParDifficulty> scoreParDifficulties);

    ScoreParDifficulty toScoreParDifficulty(ScoreParDifficultyDto scoreParDifficultyDto);
}
