package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import lombok.Data;

@Data
public class MoyCatCalcDto {
    private PrmCategory category;
    private Long nb;
    private Long total;

    public MoyCatCalcDto(PrmCategory category, Long nb,Long total) {
        this.category = category;
        this.nb = nb;
        this.total=total;
    }
}
