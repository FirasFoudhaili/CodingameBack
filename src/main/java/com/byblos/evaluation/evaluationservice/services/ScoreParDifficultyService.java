package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.ScorePerDifficultyDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParDifficulty;
import com.byblos.evaluation.evaluationservice.models.Test;

import java.util.List;

public interface ScoreParDifficultyService {
    List<ScorePerDifficultyDto> findByTest(Test test);

    List<ScoreParDifficulty> calculScoreParDifficulty(Long idtest);
}
