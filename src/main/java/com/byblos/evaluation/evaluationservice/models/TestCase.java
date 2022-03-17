package com.byblos.evaluation.evaluationservice.models;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class TestCase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testid;
	
	private Float pourcentage;
	private String input;
	private String expOutput;
	private Boolean status;
	
}
