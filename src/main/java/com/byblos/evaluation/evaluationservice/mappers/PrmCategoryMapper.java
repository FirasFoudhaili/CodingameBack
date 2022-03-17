package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.PrmCategoryDto;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrmCategoryMapper {

    PrmCategoryDto toPrmCategoryDto(PrmCategory prmCategory);

    List<PrmCategoryDto> toPrmCategoryDtos(List<PrmCategory> prmCategories);

    PrmCategory toPrmCategory(PrmCategoryDto prmCategoryDto);
}


