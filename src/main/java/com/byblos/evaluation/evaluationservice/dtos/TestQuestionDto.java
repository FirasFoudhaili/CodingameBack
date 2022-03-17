package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.TestQuestionKeys;
import lombok.Data;

@Data
public class TestQuestionDto {

    private TestQuestionKeys id;
    private Long result;
    private TestDto test;
    private QuestionDto question;
}
