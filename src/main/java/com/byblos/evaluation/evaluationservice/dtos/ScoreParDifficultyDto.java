package com.byblos.evaluation.evaluationservice.dtos;


import lombok.Data;


@Data
public class ScoreParDifficultyDto {
    private Long id;
    private double score;
    private TestDto test;
    private PrmDifficultyDto prmDifficulty;
}
