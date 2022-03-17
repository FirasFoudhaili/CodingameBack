package com.byblos.evaluation.evaluationservice.services;

import java.util.List;

import com.byblos.evaluation.evaluationservice.dtos.PrmDifficultyDto;
import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;

public interface PrmDifficultyService {
    PrmDifficulty save(PrmDifficultyDto p);
    void deleteDifficulty(Long id );
    List<PrmDifficultyDto> findAll();
    PrmDifficultyDto findById(Long id);
    PrmDifficultyDto updateDifficulty (PrmDifficultyDto prmDifficultyDto, Long id);
    PrmDifficulty getPrmDifficulty(String prmDifficultyName);
}

