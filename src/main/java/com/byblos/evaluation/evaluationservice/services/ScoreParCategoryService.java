package com.byblos.evaluation.evaluationservice.services;
import com.byblos.evaluation.evaluationservice.dtos.ScorePerCategoryDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParCategory;
import com.byblos.evaluation.evaluationservice.models.Test;

import java.util.List;

public interface ScoreParCategoryService {
    List<ScorePerCategoryDto> findByTest(Test test);
    

    List<ScoreParCategory> calculScoreParDifficulty(Long idtest);
}
