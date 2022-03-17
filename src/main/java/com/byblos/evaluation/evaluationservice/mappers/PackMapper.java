package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.PackDto;
import com.byblos.evaluation.evaluationservice.models.Pack;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel="spring")
public interface PackMapper {

    PackDto toPackDto(Pack pack);

    List<PackDto> toPackDtos(List<Pack> packs);

    Pack toPack(PackDto packDto);
}
