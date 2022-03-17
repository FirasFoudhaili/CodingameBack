package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.Company;
import lombok.Data;

@Data
public class PackDto {
    private Long id;
    private String name;
    private PrmLevelDto prmLevel;
    private Company company;
}
