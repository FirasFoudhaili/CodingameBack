package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.PrmTechnologyDto;
import com.byblos.evaluation.evaluationservice.models.PrmTechnology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrmTechnologyMapper {
    PrmTechnologyDto toPrmTechnologyDto(PrmTechnology prmTechnology);

     List<PrmTechnologyDto> toPrmTechnologyDtos(List<PrmTechnology> prmTechnologies);

    PrmTechnology toPrmTechnology(PrmTechnologyDto prmTechnologyDto);
}


