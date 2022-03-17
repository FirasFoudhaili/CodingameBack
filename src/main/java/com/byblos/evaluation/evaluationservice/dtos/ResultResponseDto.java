package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class ResultResponseDto {
    private Long id;
    private String value;
    private Boolean correct;
    private Boolean checked;
}
