package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;
import com.byblos.evaluation.evaluationservice.models.PrmLevel;

import java.util.List;

public interface PrmLevelService {
    PrmLevel save(PrmLevelDto p);
    void deleteLevel(Long id );
    List<PrmLevelDto> findAll();
    PrmLevelDto findById(Long id);
    PrmLevelDto updateLevel (PrmLevelDto prmLevelDto, Long id);
    PrmLevel  getLevel(String levelName);
            
}
