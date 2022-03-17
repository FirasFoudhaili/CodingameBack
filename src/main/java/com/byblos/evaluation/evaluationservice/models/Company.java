package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

@Data
@Entity
public class Company implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String domain;
    private Boolean isVerified;
    @Column(nullable = true)
    private int tentativeness;
    @OneToOne
    @JoinColumn
    private User user;
}
