package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

@Data
@Entity
public class ScoreParCategory implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double score;
    @ManyToOne
    @JoinColumn
    private Test test;
    @ManyToOne
    @JoinColumn
    private PrmCategory prmCategory;
}
