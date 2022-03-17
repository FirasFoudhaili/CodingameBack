package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Data
@Entity
public class Test implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testid;

    private java.sql.Date creationDate;
    private Long testDuration;
    private Boolean done;
    private Boolean expired;
    private Long passageDuration;
    private Double totalScore;
    private Boolean send;
    private Date expirationDate;
    @ManyToOne
    @JoinColumn
    private Pack pack;

    @ManyToOne
    @JoinColumn
    private Candidate candidate;

  
}
