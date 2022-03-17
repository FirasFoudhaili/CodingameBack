package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;


@Data
public class QuestionDto {

	private Long questionid;
	private String statement;
	private String code;
    private PrmLevelDto prmLevel;
    private PrmCategoryDto prmCategory;
    private PrmDifficultyDto prmDifficulty;
    private PrmTechnologyDto prmTechnology;









}
