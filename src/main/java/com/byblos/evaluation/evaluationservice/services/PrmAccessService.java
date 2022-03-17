package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmAccessDto;
import com.byblos.evaluation.evaluationservice.models.PrmAccess;

import java.util.List;

public interface PrmAccessService {
    PrmAccess save(PrmAccessDto p);
    void deleteAccess(Long id );
    List<PrmAccessDto> findAll();
    PrmAccessDto findById(Long id);
    PrmAccessDto updateAccess (PrmAccessDto prmAccessDto, Long id);
}
