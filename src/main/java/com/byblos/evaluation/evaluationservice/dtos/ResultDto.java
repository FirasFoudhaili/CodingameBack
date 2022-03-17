package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class ResultDto {

    private Long id;
    private TestDto test;
    private ResponseDto response;
    private QuestionDto question;
}
