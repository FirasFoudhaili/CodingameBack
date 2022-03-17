package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class TestAccessDto {
    private Long id;
    private  int usedAccessNumber;
    private Date purchaseDate;
    private CompteEvalDto compteEval;
    private PrmAccessDto prmAccess;
}
