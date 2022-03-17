package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.ResponseDto;
import com.byblos.evaluation.evaluationservice.models.Response;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ResponseMapper {

    ResponseDto toResponseDto(Response response);


    List<ResponseDto> toResponseDtos(List<Response> responses);

    Response toResponse(ResponseDto responseDto);

}
