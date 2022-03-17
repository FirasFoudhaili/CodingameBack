package com.byblos.evaluation.evaluationservice.dtos;

import lombok.Data;

@Data
public class CandidateDto {

    private  Long id;
    private  String name;
    private  String diploma;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private Boolean isVerified;
    private String speciality;
    private CompanyDto company;
    private UserDto user;

}
