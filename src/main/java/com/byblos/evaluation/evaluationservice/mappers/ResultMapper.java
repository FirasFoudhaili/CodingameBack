package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.ResultDto;
import com.byblos.evaluation.evaluationservice.models.Result;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ResultMapper {

    ResultDto toResultDto(Result result);

    List<ResultDto> toResultDtos(List<Result> results);

    Result toResult(ResultDto resultDto);

}
