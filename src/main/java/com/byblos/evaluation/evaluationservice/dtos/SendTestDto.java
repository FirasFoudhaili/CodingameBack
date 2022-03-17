package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;


@Data
public class SendTestDto  {
    private CandidateDto candidateDto;
    private TestDto test;
}
