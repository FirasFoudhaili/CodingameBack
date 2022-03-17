package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;
@Data
@Entity
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column
    private RoleName role;



}
