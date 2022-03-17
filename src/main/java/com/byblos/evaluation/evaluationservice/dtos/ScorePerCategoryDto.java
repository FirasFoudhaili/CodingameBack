package com.byblos.evaluation.evaluationservice.dtos;


import lombok.Data;

@Data
public class ScorePerCategoryDto {
    private String categoryName;
    private double scoreCategory;
}
