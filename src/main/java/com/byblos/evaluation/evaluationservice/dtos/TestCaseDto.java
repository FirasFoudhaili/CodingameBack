package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class TestCaseDto {
	
	private Long testid;
	private Float pourcentage;
	private String input;
	private String expOutput;
	private Boolean status;
}
