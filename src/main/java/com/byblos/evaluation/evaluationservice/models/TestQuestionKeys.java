package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable

public class TestQuestionKeys implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
    Long testid;
    @Column
    Long questionid  ;
}
