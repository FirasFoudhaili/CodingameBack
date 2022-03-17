package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.Question;
import lombok.Data;

@Data
public class ResponseDto {

    private Long id;

    private String value;
    private Boolean correct;
    private Question question;

}
