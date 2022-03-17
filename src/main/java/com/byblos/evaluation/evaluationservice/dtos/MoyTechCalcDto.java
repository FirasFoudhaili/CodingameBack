package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.PrmTechnology;
import lombok.Data;

@Data
public class MoyTechCalcDto {

    public MoyTechCalcDto(PrmTechnology tech, Long nb,Long total) {
        this.tech = tech;
        this.nb = nb;
        this.total=total;
    }

    private PrmTechnology tech;
    private Long nb;
    private Long total;
}
