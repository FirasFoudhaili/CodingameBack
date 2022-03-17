package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.PrmAccessDto;
import com.byblos.evaluation.evaluationservice.models.PrmAccess;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel="spring")
public interface PrmAccessMapper {
   PrmAccessDto toPrmAccessDto(PrmAccess prmAccess);

    List<PrmAccessDto> toPrmAccessDtos(List<PrmAccess> prmAccesses);

    PrmAccess toPrmAccess(PrmAccessDto prmAccessDto);
}





