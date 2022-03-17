package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

@Data
@Entity
public class TestQuestion implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
    private TestQuestionKeys id;
    private Long result;
    @ManyToOne
    @JoinColumn
    private Test test;
    @ManyToOne
    @JoinColumn
    private Question question;

}
