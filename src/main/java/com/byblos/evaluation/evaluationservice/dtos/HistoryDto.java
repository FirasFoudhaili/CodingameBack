package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;


@Data
public class HistoryDto {

    private Long id;
    private int intelligenceScore;
    private int knowledgeScore;
    private int totalScore;
    private TestDto test;
}
