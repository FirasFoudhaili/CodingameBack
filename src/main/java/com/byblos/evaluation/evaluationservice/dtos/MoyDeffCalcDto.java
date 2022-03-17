package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;
import lombok.Data;

@Data
public class MoyDeffCalcDto {
    private PrmDifficulty difficulty;
    private Long nb;
    private Long total;

    public MoyDeffCalcDto(PrmDifficulty difficulty, Long nb,Long total) {
        this.difficulty = difficulty;
        this.nb = nb;
        this.total=total;
    }
}
