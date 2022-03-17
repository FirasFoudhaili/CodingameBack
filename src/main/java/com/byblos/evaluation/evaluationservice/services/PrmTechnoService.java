package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmTechnologyDto;
import com.byblos.evaluation.evaluationservice.models.PrmTechnology;

import java.util.List;

public interface PrmTechnoService {
    PrmTechnology save(PrmTechnologyDto p);
    void deleteTechno(Long id);
    List<PrmTechnologyDto> findAll();
    PrmTechnologyDto findById(Long id);
    PrmTechnologyDto updateTechno (PrmTechnologyDto prmTechnologyDto, Long id);
    PrmTechnology getPrmTechnology(String prmTechnologyName);
}
