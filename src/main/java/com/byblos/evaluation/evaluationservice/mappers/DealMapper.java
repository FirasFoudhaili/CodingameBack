package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.DealDto;
import com.byblos.evaluation.evaluationservice.models.Deal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface DealMapper {


    DealDto toDealDto(Deal deal);
    List<DealDto> toDealDtos(List<Deal> deals);

    Deal toDeal(DealDto dealDto);
}



