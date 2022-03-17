package com.byblos.evaluation.evaluationservice.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionid;
	private String statement;
	@Column
	private String code;
	@ManyToOne
	@JoinColumn
	private PrmLevel prmLevel;
	@ManyToOne
	@JoinColumn
	private PrmCategory prmCategory;
	@ManyToOne
	@JoinColumn
	private PrmDifficulty prmDifficulty;
	@ManyToOne
	@JoinColumn
	private PrmTechnology prmTechnology;

}
