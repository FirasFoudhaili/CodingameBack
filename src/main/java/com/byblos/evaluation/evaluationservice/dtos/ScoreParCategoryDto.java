package com.byblos.evaluation.evaluationservice.dtos;


import lombok.Data;


@Data
public class ScoreParCategoryDto {
    private Long id;
    private double score;
    private TestDto test;
    private PrmCategoryDto prmCategory;
}
