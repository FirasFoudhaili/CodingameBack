package com.byblos.evaluation.evaluationservice.services;
import com.byblos.evaluation.evaluationservice.dtos.ResultDto;
import com.byblos.evaluation.evaluationservice.dtos.TestResponsesDto;
import com.byblos.evaluation.evaluationservice.models.Result;

import java.util.List;

import com.byblos.evaluation.evaluationservice.dtos.TestDto;



public interface ResultService {
    TestResponsesDto save(TestResponsesDto res);

    void deleteResult(Long id);

    List<ResultDto> findAll();

    ResultDto findById(Long id);

    ResultDto updateResult(ResultDto resultDto, Long id);


    Result findResultByTest(TestDto testdto);


    Boolean existsByTestAndResponse(Long test, Long response);
}