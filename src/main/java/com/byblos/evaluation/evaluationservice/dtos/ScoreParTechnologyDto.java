package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;


@Data
public class ScoreParTechnologyDto {
    private Long id;
    private double score;
    private TestDto test;
    private PrmTechnologyDto prmTechnology;
}
