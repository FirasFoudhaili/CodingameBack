package com.byblos.evaluation.evaluationservice.models;


import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Data
@Entity
public class TestAccess implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  int usedAccessNumber;
    private Date purchaseDate;
    @ManyToOne
    @JoinColumn
    private CompteEval compteEval;
    @ManyToOne
    @JoinColumn
    private PrmAccess prmAccess;

}
