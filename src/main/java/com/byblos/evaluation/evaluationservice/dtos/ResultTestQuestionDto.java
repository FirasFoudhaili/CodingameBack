package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResultTestQuestionDto {
    private Long id;
    private String statement;
    private String technologyname;
    private String code;
    private Boolean result;
    private List<ResultResponseDto> resultResponseDtoList;
    private Double scoreTotal;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
}
