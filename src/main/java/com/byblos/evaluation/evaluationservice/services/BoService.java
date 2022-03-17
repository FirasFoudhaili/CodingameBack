package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.BoDto;
import com.byblos.evaluation.evaluationservice.models.Bo;

import java.util.List;

public interface BoService {
    Bo save(BoDto p);
    void deleteBo(Long id );
    List<BoDto> findAll();
    BoDto findById(Long id);
    BoDto updateBo (BoDto boDto, Long id);
}
