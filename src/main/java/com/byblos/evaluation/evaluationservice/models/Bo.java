package com.byblos.evaluation.evaluationservice.models;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Bo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn
    private User user;

}
