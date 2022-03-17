package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

@Data
@Entity
public class Candidate implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  String diploma;
    private Integer age;
    private String gender;
    private String speciality;
    @Column(nullable = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn
    private Company company;
    @OneToOne
    @JoinColumn
    private User user;
}
