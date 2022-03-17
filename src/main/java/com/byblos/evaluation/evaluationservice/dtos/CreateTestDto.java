package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;
import java.util.List;
@Data
public class CreateTestDto {
    private List<CandidateDto> candidateList;
    private List<QuestionDto> questionList;
    private TestDto test;
}
