package com.byblos.evaluation.evaluationservice.mappers;
import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;

import com.byblos.evaluation.evaluationservice.models.CompteEval;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel="spring")
public interface CompteEvalMapper {

    CompteEvalDto toCompteEvalDto(CompteEval compteEval);

    List<CompteEvalDto> toCompteEvalDtos(List<CompteEval> compteEvals);

    CompteEval toCompteEval(CompteEvalDto compteEvalDto);
    


}
