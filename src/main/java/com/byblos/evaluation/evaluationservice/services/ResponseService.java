package com.byblos.evaluation.evaluationservice.services;
import com.byblos.evaluation.evaluationservice.dtos.ResponseDto;
import com.byblos.evaluation.evaluationservice.models.Response;

import java.util.List;

public interface ResponseService {
    Response save(ResponseDto res);
    void deleteResponse(Long id );
    void deleteResponseByQuestionId(Long id );
    List<ResponseDto> findAll();
    List<ResponseDto> getResponses(Long id);
    ResponseDto findById(Long id);
    ResponseDto updateResponse (ResponseDto responseDto, Long id);
}
