package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.ScoreParCategoryDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ScoreParCategoryMapper {
    ScoreParCategoryDto toScoreParCategoryDto(ScoreParCategory scoreParCategory);

    List<ScoreParCategoryDto> toScoreParCategoryDtos(List<ScoreParCategory> scoreParCategories);

    ScoreParCategory toScoreParCategory(ScoreParCategoryDto scoreParCategoryDto);
}
