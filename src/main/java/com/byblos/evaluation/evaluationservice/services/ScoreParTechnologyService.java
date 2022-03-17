package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.ScorePerTechnologyDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParTechnology;
import com.byblos.evaluation.evaluationservice.models.Test;

import java.util.List;

public interface ScoreParTechnologyService {
    List<ScorePerTechnologyDto> findByTest(Test test);

    List<ScoreParTechnology> calculScoreParTechnology(Long idtest);
}
