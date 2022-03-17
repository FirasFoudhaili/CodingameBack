package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class CompanyDto {


    private Long id;
    private String name;
    private String domain;
    private UserDto user;
    private Boolean isVerified;
    private int tentativeness;
}
