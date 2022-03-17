package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.models.Test;
import lombok.Data;

import java.util.List;
@Data
public class TestResponsesDto {
    Test test;
    Question question;
    List<Response> responses;
    int index;
    Long testDuration;
    Boolean timeOver;
}
