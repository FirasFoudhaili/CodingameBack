package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CompteEvalDto {

        private Long id;
        private Date expirationDate;
        private DealDto deal;
        private CompanyDto company;

    }
