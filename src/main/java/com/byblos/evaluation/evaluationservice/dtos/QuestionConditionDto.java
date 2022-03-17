package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class QuestionConditionDto  {
    private PrmLevelDto prmLevelDto;
   private PrmCategoryDto prmCategoryDto;
    private PrmDifficultyDto prmDifficultyDto;
   private PrmTechnologyDto prmTechnologyDto;
    private Long nbQuestions;
    
}

