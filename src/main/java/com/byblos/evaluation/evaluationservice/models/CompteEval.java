package com.byblos.evaluation.evaluationservice.models;


import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class CompteEval implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date expirationDate;
    @ManyToOne()
    @JoinColumn
    private Deal deal;
    @OneToOne()
    @JoinColumn
    private Company company;







}
